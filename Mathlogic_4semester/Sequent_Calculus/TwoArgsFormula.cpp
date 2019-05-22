//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#include "TwoArgsFormula.h"

TwoArgsFormula::TwoArgsFormula(Formula *leftArg, Formula *rightArg, operandType typeOfOperand) {
    this->rightArg = rightArg;
    this->leftArg = leftArg;
    this->typeOfOperand = typeOfOperand;
}

std::string TwoArgsFormula::toString() {
    std::string result = "(" + leftArg->toString();
    switch(typeOfOperand) {
        case Conjunction:
#ifdef UNICODE
            result += " \u2228 ";
#else
            result += " ^ ";
#endif
            break;
        case Disjunction:
#ifdef UNICODE
            result += " \u2227 ";
#else
            result += " v ";
#endif
            break;
        case Implication:
#ifdef UNICODE
            result += " \u2192 ";
#else
            result += " -> ";
#endif

            break;
    }
    result += rightArg->toString() + ")";
    return result;
}

Formula* TwoArgsFormula::getLeftArg() {
    return leftArg;
}

Formula* TwoArgsFormula::getRightArg() {
    return rightArg;
}
