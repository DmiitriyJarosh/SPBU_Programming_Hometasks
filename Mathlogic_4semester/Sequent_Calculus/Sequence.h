//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#ifndef SEQUENCE_SEQUENCE_H
#define SEQUENCE_SEQUENCE_H


#include "Formula.h"
#include <vector>

class Sequence {
private:
    std::vector<Formula*> *leftArg;
    std::vector<Formula*> *rightArg;
public:
    Sequence(int leftArgSize, int rightArgSize);
    Sequence(std::vector<Formula*> *leftArg, std::vector<Formula*> *rightArg);
    std::pair<Sequence*, Sequence*> *getParentSequnces();
    std::string toString();
    void deleteDuplicates();
    std::vector<Formula*> * getLeftArg();
    std::vector<Formula*> * getRightArg();
};


#endif //SEQUENCE_SEQUENCE_H
