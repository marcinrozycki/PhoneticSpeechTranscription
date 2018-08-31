import argparse
import os


class TranscriptionFileCreator:

    @staticmethod
    def convert_file_into_transcription_file(input_word_file, output_transcription_file):
        with open(input_word_file, 'r') as wordFile:
            word_lines = wordFile.readlines()
        with open(output_transcription_file, 'w') as transcriptionFile:
            line_number = 1
            for line in word_lines:
                line = line.strip()
                line_format = "<s> {} </s> ({}-{})\n"
                input_word_file_name = os.path.basename(input_word_file).replace(".txt", "")
                line_number_ending = str(line_number).zfill(3)
                new_line = line_format.format(line, input_word_file_name, line_number_ending)
                transcriptionFile.write(new_line)
                line_number += 1


def main():
    parser = argparse.ArgumentParser(description='Process dictionary file')
    parser.add_argument('inputWordFile', type=str, help='dictionary file path')
    parser.add_argument('outputTranscriptionFile', type=str, help='transcription file path')

    args = parser.parse_args()
    input_word_file = args.inputWordFile
    transcription_file = args.outputTranscriptionFile
    creator = TranscriptionFileCreator()
    creator.convert_file_into_transcription_file(input_word_file, transcription_file)


if __name__ == '__main__':
    main()
