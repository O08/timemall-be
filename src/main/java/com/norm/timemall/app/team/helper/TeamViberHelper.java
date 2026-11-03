package com.norm.timemall.app.team.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.enums.AppViberFileSceneEnum;
import com.norm.timemall.app.base.enums.AppViberPostEmbedFacetEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.team.domain.pojo.*;

import java.util.*;
import java.util.function.Function;

public class TeamViberHelper {

    private static final Map<String, Function<TeamAppViberPostEmbed, List<String>>> LINK_EXTRACTORS;
    private static final Map<String, Function<TeamAppViberPostEmbed, Boolean>> FACET_EMBED_CONTENT_EMPTY_CHECKER;
    private static final Map<String, Function<TeamAppViberPostEmbed, Boolean>> FACET_EMBED_CONTENT_SIZE_CHECKER;

    private static final Set<String> PRIVATE_FILE_SCENES;


    static {
        // 1. Initialize link Extractors
        Map<String, Function<TeamAppViberPostEmbed, List<String>>> extractorMap = new HashMap<>();

        extractorMap.put(AppViberPostEmbedFacetEnum.ATTACHMENT.getCode(),
                e -> safeExtract(e.getAttachments(), TeamAppViberPostEmbedAttachment::getUrl));

        extractorMap.put(AppViberPostEmbedFacetEnum.IMAGE.getCode(),
                e -> safeExtract(e.getImages(), TeamAppViberPostEmbedImage::getLink));

        extractorMap.put(AppViberPostEmbedFacetEnum.COMIC.getCode(),
                TeamViberHelper::safeExtractForComic);

        extractorMap.put(AppViberPostEmbedFacetEnum.LOCAL_VIDEO.getCode(),
                e -> safeExtract(e.getVideos(), TeamAppViberPostEmbedVideo::getUrl));

        extractorMap.put(AppViberPostEmbedFacetEnum.LOCAL_AUDIO.getCode(),
                e -> safeExtract(e.getAudios(), TeamAppViberPostEmbedAudio::getUrl));

        LINK_EXTRACTORS = Collections.unmodifiableMap(extractorMap);

        // 2. Initialize Empty Checkers
        Map<String, Function<TeamAppViberPostEmbed, Boolean>> emptyCheckerMap = new HashMap<>();

        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.ATTACHMENT.getCode(), e -> CollUtil.isEmpty(e.getAttachments()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.IMAGE.getCode(), e -> CollUtil.isEmpty(e.getImages()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.THIRD_PARTY_IMAGE.getCode(), e -> CollUtil.isEmpty(e.getImages()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.THIRD_PARTY_VIDEO.getCode(), e -> CollUtil.isEmpty(e.getVideos()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.THIRD_PARTY_AUDIO.getCode(), e -> CollUtil.isEmpty(e.getAudios()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.LINK.getCode(), e -> CollUtil.isEmpty(e.getLinks()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.COMIC.getCode(), e -> ObjectUtil.isNull(e.getComic()) || CharSequenceUtil.isBlank(e.getComic().getTitle()) || CharSequenceUtil.isBlank(e.getComic().getCover()) || CharSequenceUtil.isBlank(e.getComic().getChapter()) || CollUtil.isEmpty(e.getComic().getImages()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.LOCAL_VIDEO.getCode(), e -> CollUtil.isEmpty(e.getVideos()));
        emptyCheckerMap.put(AppViberPostEmbedFacetEnum.LOCAL_AUDIO.getCode(), e -> CollUtil.isEmpty(e.getAudios()));

        FACET_EMBED_CONTENT_EMPTY_CHECKER = Collections.unmodifiableMap(emptyCheckerMap);

        // 3. Initialize Oversize Checkers
        Map<String, Function<TeamAppViberPostEmbed, Boolean>> sizeCheckerMap = new HashMap<>();

        // max 9
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.ATTACHMENT.getCode(), e -> CollUtil.size(e.getAttachments()) > 9);
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.THIRD_PARTY_AUDIO.getCode(), e -> CollUtil.size(e.getAudios()) > 9);
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.LOCAL_AUDIO.getCode(), e -> CollUtil.size(e.getAudios()) > 9);


        // max 50
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.IMAGE.getCode(), e -> CollUtil.size(e.getImages()) > 50);
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.THIRD_PARTY_IMAGE.getCode(), e -> CollUtil.size(e.getImages()) > 50);
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.COMIC.getCode(), e -> CollUtil.size(e.getComic().getImages()) > 50);


        // max 1
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.THIRD_PARTY_VIDEO.getCode(), e -> CollUtil.size(e.getVideos()) >= 2);
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.LINK.getCode(), e -> CollUtil.size(e.getLinks()) >= 2);
        sizeCheckerMap.put(AppViberPostEmbedFacetEnum.LOCAL_VIDEO.getCode(), e -> CollUtil.size(e.getVideos()) >= 2);


        FACET_EMBED_CONTENT_SIZE_CHECKER = Collections.unmodifiableMap(sizeCheckerMap);

        // 4. Initialize PRIVATE_FILE_SCENES
        PRIVATE_FILE_SCENES = Set.of(
                AppViberFileSceneEnum.ATTACHMENT.getMark(),
                AppViberFileSceneEnum.COMIC.getMark(),
                AppViberFileSceneEnum.LOCAL_AUDIO.getMark(),
                AppViberFileSceneEnum.LOCAL_VIDEO.getMark()
        );

    }





    private TeamViberHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // getLinksFromEmbedWhenDel code section start

    public static List<String> getLocalLinksFromEmbed(TeamAppViberPostEmbed embed) {
        if (embed == null) return Collections.emptyList();
        return LINK_EXTRACTORS
                .getOrDefault(embed.getFacet(), e -> Collections.emptyList())
                .apply(embed);
    }



    private static <T> List<String> safeExtract(List<T> list, Function<T, String> mapper) {
        return CollUtil.emptyIfNull(list).stream()
                .filter(Objects::nonNull)
                .map(mapper)
                .filter(CharSequenceUtil::isNotBlank)
                .toList();
    }

    private static List<String> safeExtractForComic(TeamAppViberPostEmbed embed) {
        List<String> links = new ArrayList<>();
        links.add(embed.getComic().getCover());

        List<String>  imagesLinks = safeExtract(embed.getComic().getImages(), TeamAppViberPostEmbedImage::getLink);
        links.addAll(imagesLinks);
        return links;
    }

    // getLinksFromEmbedWhenDel code section end

    // doContentCheckWhenEmbedNotNull code section start

    public static void doContentCheckWhenEmbedNotNull(TeamAppViberPostEmbed embed){
        if (embed == null) {
            return;
        }
        String facet = embed.getFacet();

        // 1. Is the type known?
        if (!AppViberPostEmbedFacetEnum.validation(facet)) {
            throw new QuickMessageException("Invalid embed facet");
        }

        // 2. Is the content for that type empty?
        // Note: Default to 'true' (invalid) if the facet is missing from the map
        boolean isContentEmpty = FACET_EMBED_CONTENT_EMPTY_CHECKER
                .getOrDefault(facet, e -> true)
                .apply(embed);

        if (isContentEmpty) {
            throw new QuickMessageException("Invalid embed content");
        }
    }




    // doContentCheckWhenEmbedNotNull code section end


    // doPostSizeCheck code section start
    public static boolean doPostSizeCheck(TeamAppViberPostEmbed embed){
        if(embed==null) return false;
        return  FACET_EMBED_CONTENT_SIZE_CHECKER
                .getOrDefault(embed.getFacet(), e -> false)
                .apply(embed);
    }



    // doPostSizeCheck code section end


    // isPrivateFile code section start


    public static boolean isPrivateFile(String scene) {
        if(scene==null) return  false;
        return PRIVATE_FILE_SCENES.contains(scene);
    }

    // isPrivateFile code section end


}
