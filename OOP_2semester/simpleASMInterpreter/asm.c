#include "asm.h"

main()
{
    char* filename = (char*)malloc(LENGTH_OF_STRING);
    printf("Welcome to ASM!\n");
    init();
    printf("Initialization successfully completed! Enter name of your file:\n");
    gets(filename);
    if ((input = fopen(filename, "r")) == NULL)
    {
        printf("File error");
        exit(0);
    }
    getCode();
    while (analyzeAndDoCode(code.IP))
    {
        if (code.point == code.IP)
        {
            getCode();
        }
    }
    deinit();
    fclose(input);
    printf("Program successfully finished!\n");
    return 0;
}
