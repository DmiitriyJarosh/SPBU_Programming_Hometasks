//
// Created by Dmitrii Iarosh on 08.03.2019.
//

#ifndef SEQUENCE_FILEPRINTER_H
#define SEQUENCE_FILEPRINTER_H


#include <fstream>

class FilePrinter {
private:
    std::ofstream fout;

public:
    FilePrinter(std::string filename) {
        fout.open(filename);
    }
    void printToFile(std::string text){
        fout << text;
        fout.flush();
    }
    ~FilePrinter() {
        fout.close();
    }
};


#endif //SEQUENCE_FILEPRINTER_H
