//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#ifndef SEQUENCE_FORMULA_H
#define SEQUENCE_FORMULA_H

#include <string>

enum operandType { Conjunction, Disjunction, Implication, Negation, PropVariable };

class Formula {

protected:
    operandType typeOfOperand;

public:
    virtual std::string toString() = 0;
    operandType getOperandType() {
        return typeOfOperand;
    }
};

#endif //SEQUENCE_FORMULA_H
