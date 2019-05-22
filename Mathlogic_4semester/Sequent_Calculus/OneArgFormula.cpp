//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#include "OneArgFormula.h"

OneArgFormula::OneArgFormula(Formula *arg, operandType typeOfOperand) {
    this->arg = arg;
    this->typeOfOperand = typeOfOperand;
}

std::string OneArgFormula::toString() {
    std::string result;
    switch (typeOfOperand) {
        case Negation:
#ifdef UNICODE
            if (arg->getOperandType() != PropVariable) {
                result = "\u00AC(" + arg->toString() + ")";
            } else {
                result = "\u00AC" + arg->toString();
            }
#else
            if (arg->getOperandType() != PropVariable) {
                result = "!(" + arg->toString() + ")";
            } else {
                result = "!" + arg->toString();
            }
#endif
            break;
    }
    return result;
}

Formula* OneArgFormula::getArg() {
    return arg;
}