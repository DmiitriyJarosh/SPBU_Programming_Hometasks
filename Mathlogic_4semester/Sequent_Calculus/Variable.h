//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#ifndef SEQUENCE_VARIABLE_H
#define SEQUENCE_VARIABLE_H


#include "Formula.h"

class Variable : public Formula {
private:
    std::string name;
public:
    Variable(std::string& name);
    std::string toString();
    bool operator== (const Variable &rightArg);
};


#endif //SEQUENCE_VARIABLE_H
