#include<bits/stdc++.h>
using namespace std;

class PriorityQueue     //binary heap
{
    pair<int,int> x[100];
    int c;

    bool smaller(int a,int b)
    {
        if(x[a].first!=x[b].first) return x[a].first<x[b].first;
        return x[a].second<x[b].second;
    }

    void swap(int i,int m)
    {
        int t=x[m].first;
        x[m].first=x[i].first;
        x[i].first=t;

        t=x[m].second;
        x[m].second=x[i].second;
        x[i].second=t;
    }

    void heapify(int i)
    {
        int m=i,l=2*i+1,r=2*i+2;
        if(l<c && smaller(l,m)) m=l;
        if(r<c && smaller(r,m)) m=r;
        if(m!=i)
        {
            swap(i,m);
            heapify(m);
        }
    }

public:
    PriorityQueue()
    {
        c=0;
    }

    void add(int a,int b)   //add an element to heap
    {
        x[c]={a,b};
        c++;
        int i=c-1;
        while(i!=0 && smaller(i,(i-1)/2))
        {
            swap((i-1)/2,i);
            i=(i-1)/2;
        }
    }

    bool isEmpty()
    {
        return c==0;
    }

    pair<int,int> peek()
    {
        return x[0];
    }

    pair<int,int> poll()    //it can be assured that this call will be made only when heap is not empty
    {
        pair<int,int> ans={x[0].first,x[0].second};
        x[0]={x[c-1].first,x[c-1].second};
        c--;
        heapify(0);
        return ans;
    }
};

int main()
{
    int n,m;
    cin>>n>>m;
    vector<pair<int,int>> g[n];
    int i,dp[n];
    for(i=0;i<n;i++) dp[i]=INT32_MAX;

    for(i=0;i<m;i++)
    {
        int u,v,w;
        cin>>u>>v>>w;
        u--; v--;
        g[u].push_back({v,w});
        //g[v].push_back({u,w});
    }

    int sr;
    cin>>sr;
    sr--;
    PriorityQueue pq;
    pq.add(0,sr);
    while(!pq.isEmpty())
    {
        pair<int,int> p=pq.poll();
        int wt=p.first; i=p.second;
        if(dp[i]<wt) continue;
        dp[i]=wt;
        for(auto x:g[i])
        if(wt+x.second<dp[x.first]) pq.add(wt+x.second,x.first);
    }

    //cout<<"Distances from source are:";
    for(i=0;i<n;i++)
    cout<<dp[i]<<" ";
    return 0;
}