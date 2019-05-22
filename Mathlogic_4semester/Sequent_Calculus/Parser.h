//
// Created by Dmitrii Iarosh on 08.03.2019.
//

#ifndef SEQUENCE_PARSER_H
#define SEQUENCE_PARSER_H


#include "TwoArgsFormula.h"
#include "OneArgFormula.h"
#include "Variable.h"
#include <set>

class Parser {
private:
    std::set<Variable*> *variables;
public:
    Parser();
    std::set<Variable*>* getVariables();
    Formula* parse(std::string input);
    TwoArgsFormula* parseTwoArgumentFunction(std::string input, int posOfOperand);
    OneArgFormula* parseOneArgumentFormula(std::string input);
    Variable* parseVariable(std::string input);
};


#endif //SEQUENCE_PARSER_H
