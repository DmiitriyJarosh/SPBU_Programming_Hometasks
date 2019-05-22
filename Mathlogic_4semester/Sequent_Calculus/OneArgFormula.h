//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#ifndef SEQUENCE_ONEARGFORMULA_H
#define SEQUENCE_ONEARGFORMULA_H


#include "Formula.h"

class OneArgFormula : public Formula {
private:
    Formula *arg;
public:
    OneArgFormula(Formula *arg, operandType typeOfOperand);
    std::string toString();
    Formula* getArg();
};


#endif //SEQUENCE_ONEARGFORMULA_H
