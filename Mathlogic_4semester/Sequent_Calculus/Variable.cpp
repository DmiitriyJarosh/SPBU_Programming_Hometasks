//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#include "Variable.h"

Variable::Variable(std::string& name) {
    this->name = name;
    this->typeOfOperand = PropVariable;
}

bool Variable::operator==(const Variable &rightArg) {
    return this->name == rightArg.name;
}

std::string Variable::toString() {
    return name;
}