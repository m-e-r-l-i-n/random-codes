'''
a random number generator can be made truly random using time as the seed
but we are not going to use any library functions, so we are going to use linear feedback shift register
lfsr are specified entirely by polynomials
we will use a larger lfsr and then generate smaller numbers using that
'''

pm32=3032273756 #poly_mask_32=0xB4BCD35C
pm31=2052834019 #poly_mask_31=0x7A5BC2E3

#seed values
lb=703710  #lfsr32=0xABCDE
ls=591751049  #lfsr31=0x23456789

def shift_lfsr(mask,l):
    fb=0    #feedback
    l>>=1
    if fb==1: l^=mask
    return l


def rand_int(n):
    if n==0: return 0
    global lb,ls
    lb=shift_lfsr(pm32,lb)
    lb=shift_lfsr(pm32,lb)
    ls=shift_lfsr(pm31,ls)
    if lb==0 and ls==0: #after a point of time, both of them 0 out, so we go again
        lb=703710
        ls=591751049
    return (lb^ls^65535)%n

if __name__=='__main__':
    n=int(input())
    for _ in range(10):
        print(rand_int(n))

'''
input-
5

output-
1
3
2
1
4
2
3
2
3
0
 
'''