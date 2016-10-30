package org.outrospective.dreamstime.reconciler

enum ImageSize {

    TIFF('.tif', null), ADDITIONAL('.eps',null), MAXIMUM('.jpg','xxl'), EXTRASMALL('.jpg','xs'), LARGE('.jpg', 'l')

    def extension
    def abbreviation

    ImageSize(extension, abbreviation) {
        this.extension = extension
        this.abbreviation = abbreviation
    }

    boolean hasAbbreviation() {
        abbreviation != null
    }

    def fileAbbreviation() {
        hasAbbreviation() ? "_${abbreviation}" : ""
    }
}