#include<bits/stdc++.h>
using namespace std;

int partition(int a[],int l,int r)
{
    int pivot=a[r],i=l-1,j;
    for(j=l;j<r;j++)
    if(a[j]<=pivot)
    {
        i++;
        a[i]=a[i]^a[j]^(a[j]=a[i]);
    }
    a[i+1]=a[i+1]^a[r]^(a[r]=a[i+1]);
    return (i+1);
}

void quicksort(int a[],int l,int r)
{
    if(l<r)
    {
        int p=partition(a,l,r);
        quicksort(a,l,p-1);
        quicksort(a,p+1,r);
    }
}

int main()
{
    int n,i;
    cin>>n;
    int a[n];
    for(i=0;i<n;i++)
    cin>>a[i];

    quicksort(a,0,n-1);
    for(i=0;i<n;i++)
    cout<<a[i]<<" ";
    return 0;
}