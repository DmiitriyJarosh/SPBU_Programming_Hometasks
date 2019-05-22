#pragma once
#include "asm.h"

int isCmd(int IP, int i, char* command)
{
    int j, flag = 1;
    int length = strlen(command);
    for (j = 0; j < length; j++)
    {
        if (command[j] != code.memory[IP][i])
        {
            flag = 0;
            break;
        }
        i++;
    }
    if (flag)
    {
        if (code.memory[IP][i] != ' ' && code.memory[IP][i] != '\n' && code.memory[IP][i] != '\0')
        {
            flag = 0;
        }
    }
    return flag;
}

int analyzeAndDoCode(int IP)
{
    int i, flag = 1, wrongCommand = 0;
    if (checkPoint(code.memory[IP]))
    {
        i = takeTag(code.memory[IP]);
    }
    else
    {
        i = 0;
    }
    switch (code.memory[IP][i])
    {
        case 'r':
            if (isCmd(IP, i, "ret"))
            {
                flag = 0;
            }
            else
            {
                wrongCommand = 1;
            }
        break;
        case 'l':
            if (isCmd(IP, i, "ldc"))
            {
                ldc(charToInt(code.memory[IP], i + 4));
            }
            else
            {
                if (isCmd(IP, i, "ld"))
                {
                    ld(charToInt(code.memory[IP], i + 3));
                }
                else
                {
                    wrongCommand = 1;
                }
            }
        break;
        case 's':
            if (isCmd(IP, i, "st"))
            {
                st(charToInt(code.memory[IP], i + 3));
            }
            else
            {
                if (isCmd(IP, i, "sub"))
                {
                    sub();
                }
                else
                {
                    wrongCommand = 1;
                }
            }
        break;
        case 'a':
            if (isCmd(IP, i, "add"))
            {
                add();
            }
            else
            {
                wrongCommand = 1;
            }
        break;
        case 'p':
            if (isCmd(IP, i, "print"))
            {
                print();
            }
            else
            {
                wrongCommand = 1;
            }
        break;
        case 'c':
            if (isCmd(IP, i, "cmp"))
            {
                cmp();
            }
            else
            {
                wrongCommand = 1;
            }
        break;
        case 'j':
            if (isCmd(IP, i, "jmp"))
            {
                jmp(readTag(code.memory[IP], i + 4));
            }
            else
            {
                wrongCommand = 1;
            }
        break;
        case 'b':
            if (isCmd(IP, i, "br"))
            {
                br(readTag(code.memory[IP], i + 3));
            }
            else
            {
                wrongCommand = 1;
            }
        break;
        case ';':
        break;
        default:
            {
                printf("Unknown command");
                exit(0);
            }
    }
    if (wrongCommand == 1)
    {
        printf("Unknown command");
        exit(0);
    }
    if (code.IP == IP)
    {
        code.IP++;
    }
    return flag;
}
