fun main(args: Array<String>) {
    val settings = Arguments()
    parseArgs(args, settings)
    val reader = Reader()
    val processor = Processor(reader)
    if (settings.fileCheck) processor.singleFileProcessor(settings, reader)
    else processor.directoryProcessor(settings, reader)
}