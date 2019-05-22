#include <iostream>
using namespace std;

main()
{
    int a = 10, b = 0, i = 1;
    while (a > 0)
    {
        b += (a % 2) * i;
        i *= 10;
        a = a / 2;
    }
    cout << b;
    return 0;
}

