var a = 10;
var b = 0;
var i = 1;
while (a > 0)
{
   b += (a % 2) * i;
   i *= 10;
   a = Math.floor(a / 2);
}
print(b);