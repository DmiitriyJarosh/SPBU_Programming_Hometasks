//
// Created by Dmitrii Iarosh on 08.03.2019.
//

#include <iostream>
#include "Parser.h"

Parser::Parser() {
    this->variables = new std::set<Variable*>();
}

std::set<Variable*>* Parser::getVariables() {
    return variables;
}

TwoArgsFormula* Parser::parseTwoArgumentFunction(std::string input, int posOfOperand) {
    //std::cout << input << " " << input[posOfOperand];
    switch (input[posOfOperand]) {
        case '^':
            return new TwoArgsFormula(parse(input.substr(0, posOfOperand - 1)), parse(input.substr(posOfOperand + 2, input.length() - (posOfOperand + 2))), Conjunction);
        case 'v':
            return new TwoArgsFormula(parse(input.substr(0, posOfOperand - 1)), parse(input.substr(posOfOperand + 2, input.length() - (posOfOperand + 2))), Disjunction);
        case '-':
            return new TwoArgsFormula(parse(input.substr(0, posOfOperand - 1)), parse(input.substr(posOfOperand + 3, input.length() - (posOfOperand + 3))), Implication);
    }
    std::cout << "Parse error\n";
    exit(1);
}

OneArgFormula* Parser::parseOneArgumentFormula(std::string input) {
    return new OneArgFormula(parse(input.substr(1, input.length() - 1)), Negation);
}

Variable* Parser::parseVariable(std::string input) {
    auto tmp = new Variable(input);
    variables->insert(tmp);
    return tmp;
}

Formula* Parser::parse(std::string input) {
    int i = 1, posOfOperand;
    int bracketsCounter = 0;
    bool flag = true;
    switch (input[0]) {
        case '(':
            while (flag) {
                if (input[i] == '(') {
                    bracketsCounter++;
                } else if (input[i] == ')') {
                    bracketsCounter--;
                } else if (bracketsCounter == 0 && (input[i] == '-' || input[i] == 'v' || input[i] == '^')) {
                    flag = false;
                    posOfOperand = i;
                }
                i++;
            }
            //std::cout << input.substr(1, input.length() - 2) << std::endl;
            return parseTwoArgumentFunction(input.substr(1, input.length() - 2), posOfOperand - 1);
        case '!':
            //std::cout << input << std::endl;
            return parseOneArgumentFormula(input);
        default:
            //std::cout << input << std::endl;
            return parseVariable(input);
    }
}