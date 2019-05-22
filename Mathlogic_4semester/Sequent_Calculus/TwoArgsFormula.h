//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#ifndef SEQUENCE_TWOARGSFORMULA_H
#define SEQUENCE_TWOARGSFORMULA_H


#include "Formula.h"

class TwoArgsFormula : public Formula {
private:
    Formula *leftArg;
    Formula *rightArg;
public:
    TwoArgsFormula(Formula *leftArg, Formula *rightArg, operandType typeOfOperand);
    std::string toString() override;
    Formula* getLeftArg();
    Formula* getRightArg();
};


#endif //SEQUENCE_TWOARGSFORMULA_H
