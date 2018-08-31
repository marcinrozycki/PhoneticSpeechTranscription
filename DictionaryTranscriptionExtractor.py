import os
import argparse
import re


class DictionaryTranscriptionExtractor():

    def getFileContent(self, file):
        with open(file) as f:
            fileContent = f.readlines()
            fileContent = [line.strip() for line in fileContent]
            return fileContent

    def saveToFile(self, filePath, content):
        with open(filePath, "w") as file:
            file.writelines(content)

    def getUsefullData(self, dictionaryContent, transcriptionFileContent):
        outputLines = []
        for line in transcriptionFileContent:
            for dictionaryLine in dictionaryContent:
                dictionaryWord = dictionaryLine.split(" ")[0]
                if line == dictionaryWord:
                    print("Line append to file: " + str(dictionaryLine))
                    outputLines.append(dictionaryLine + "\n")
        outputLines = list(dict.fromkeys(outputLines))
        return outputLines

    def extractData(self, dictionaryFile, transcriptionFile):
        dictionaryContent = self.getFileContent(dictionaryFile)
        transcriptionContent = self.getFileContent(transcriptionFile)
        transcriptionWords = self.parseTranscriptionContent(transcriptionContent)
        content = self.getUsefullData(dictionaryContent, transcriptionWords)
        directory = os.path.dirname(dictionaryFile)
        saveFilePath = os.path.join(directory, "exportedDictionary.dic")
        self.saveToFile(saveFilePath, content)

    def parseTranscriptionContent(self, transcriptionFileContent):
        regex = r"<s> (?P<word>.*) </s>"
        lines = []
        for line in transcriptionFileContent:
            if re.match(regex, line):
                matchedLine = re.match(regex, line)
                sentence = matchedLine.group("word")
                splitted = sentence.split(" ")
                for word in splitted:
                    lines.append(word)
        diction = list(dict.fromkeys(lines))
        return diction



def main():
    parser = argparse.ArgumentParser(description='Process dictionary file')
    parser.add_argument('dictionary', type=str, help='dictionary file pathś')
    parser.add_argument('transcription', type=str, help='transcription file path')

    args = parser.parse_args()
    dictionary_file = args.dictionary
    transcription_file = args.transcription
    extractor = DictionaryTranscriptionExtractor()
    extractor.extractData(dictionary_file, transcription_file)


if __name__ == '__main__':
    main()
