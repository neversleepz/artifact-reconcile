package org.outrospective.dreamstime.reconciler

import java.nio.file.Path

class Reconciler {

    static def dryRun

    public static void main(String[] args) {
        def cli = new CliBuilder()
        cli.with {
            h longOpt: 'help', 'Show usage'
            l longOpt: 'xlsx', args: 1, argName: 'path', 'path to xlsx file downloaded from Dreamstime & converted to xlsx in Excel'
            i longOpt: 'images', args: 1, argName: 'image path', 'directory where images to be renamed appear'
            m longOpt: 'dry-run', 'Dont make any changes.'
        }

        def cliArguments = cli.parse(args)
        dryRun = cliArguments.m
        if (dryRun) println "DRYRUN: NO changes made"
        if (!cliArguments || cliArguments.h || !cliArguments.i || !cliArguments.l) {
            cli.usage(); return
        } else {
            new Reconciler().run(cliArguments.l, cliArguments.i)
        }
    }

    void run(xlsxPath, imagePath) {
        def reader = new XLSXReader(xlsxPath)
        ImageFinder imageFinder = new ImageFinder(imagePath)
        reader.readFile();

        def images = reader.values
        def expectedLength = images.size()
        println "${expectedLength} records loaded"

        images.each {
            imageRecord ->
                def id = imageRecord["File ID"]?.longValue()
                def title = sanitizeFilename(imageRecord["Title"])
                def size = ImageSize.valueOf(imageRecord["Image size"].toString().toUpperCase())

                Path foundFile = imageFinder.find(id, size)
                String newName = "dreamstime_${sanitizeFilename(title)}${size.fileAbbreviation()}_${id}${size.extension}"
                println "$foundFile => $newName"

                if (!dryRun) {
                    println "Renaming ${foundFile.renameTo(newName) ? "successful" : "failed" }"
                }
        }


    }

    public String sanitizeFilename(String inputName) {
        return inputName?.replaceAll(/[^\w-_]/, "-").replaceAll(/-$/, '');
    }
}

