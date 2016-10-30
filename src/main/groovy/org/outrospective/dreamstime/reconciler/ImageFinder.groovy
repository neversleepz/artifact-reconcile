package org.outrospective.dreamstime.reconciler

import groovy.io.FileType

import java.nio.file.Paths

class ImageFinder {
    def final PATH

    ImageFinder(path) {
        this.PATH = path
    }

    def find(long fileId, ImageSize size) {
        def filePattern = ~/dreamstime_(\p{Alpha}+_)?${fileId}\.(eps|tif|jpg)/
        def foundFile

        Paths.get(PATH).eachFileMatch(FileType.FILES, filePattern) { path ->
            def name = path.fileName.toString()
            if (name.endsWith(size.extension) && abbreviationMatches(name,size)) {
                foundFile = path
                println "Found $fileId, $size: $path"
            }
        }
        return foundFile
    }

    private boolean abbreviationMatches(String name, ImageSize size) {
        nameContainsExpectedSize(name, size) || !size.hasAbbreviation()
    }

    private boolean nameContainsExpectedSize(String name, ImageSize size) {
        size.hasAbbreviation() && name.contains("_${size.abbreviation}_")
    }
}

