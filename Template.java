import java.util.*;
class Template
{
    static class SegmentTree
    {
        int st[],l[];
        SegmentTree(int n)
        {
            st=new int[4*n];
            l=new int[4*n];
        }

        void update(int ss,int se,int us,int ue,int v,int n,int ty)
        {
            if(l[n]!=0)
            {
                if(ty==1) st[n]+=(se-ss+1)*l[n];  //check for overflows
                else st[n]+=l[n];
                if(ss!=se) {l[2*n+1]+=l[n]; l[2*n+2]+=l[n];}
                l[n]=0;
            }

            if(ss>se || se<us || ss>ue) return;
            if(ss>=us && se<=ue)
            {
                if(ty==1) st[n]+=(se-ss+1)*v; //cfo
                else st[n]+=v;
                if(ss!=se) {l[2*n+1]+=v; l[2*n+2]+=v;}
                return;
            }

            int m=ss+(se-ss)/2;
            update(ss,m,us,ue,v,2*n+1,ty); update(m+1,se,us,ue,v,2*n+2,ty);
            switch(ty)
            {
                case 1: st[n]=st[2*n+1]+st[2*n+2]; break; //cfo
                case 2: st[n]=Math.max(st[2*n+1],st[2*n+2]); break;
                case 3: st[n]=Math.min(st[2*n+1],st[2*n+2]); break;
                default: st[n]=gcd(st[2*n+1],st[2*n+2]);
            }
        }
        int gcd(int a,int b)  //use int and long as and when required
        {
            int t;
            while(b!=0)
            {
                t=b;
                b=a%b;
                a=t;
            }
            return a;
        }

        int query(int ss,int se,int qs,int qe,int n,int ty)
        {
            if(l[n]!=0)
            {
                if(ty==1) st[n]+=(se-ss+1)*l[n];  //cfo
                else st[n]+=l[n];
                if(ss!=se) {l[2*n+1]+=l[n]; l[2*n+2]+=l[n];}
                l[n]=0;
            }

            if(ss>se || qe<ss || qs>se) return ty==3?Integer.MAX_VALUE:0;
            if(ss>=qs && se<=qe) return st[n];

            int m=ss+(se-ss)/2,u=query(ss,m,qs,qe,2*n+1,ty),v=query(m+1,se,qs,qe,2*n+2,ty);
            switch(ty)
            {
                case 1: return u+v; //cfo
                case 2: return Math.max(u,v);
                case 3: return Math.min(u,v);
                default: return gcd(u,v);
            }
        }
    }

    static class KMP
    {
        String a,b;
        KMP(String x,String y)
        {
            a=x;    //txt
            b=y;    //pattern
            lps=new int[b.length()];
        }

        int lps[];
        int pattern()
        {
            int i=1,j=0,n=a.length(),m=b.length(),ans=0;
            //compute lps array
            while(i<m)
            {
                if(b.charAt(i)==b.charAt(j)) lps[i++]=++j;
                else
                {
                    if(j!=0) j=lps[j-1];
                    else lps[i++]=j;
                }
            }

            i=0; j=0;
            while(i<n)
            {
                if(b.charAt(j)==a.charAt(i)) {j++; i++;}
                if(j==m) {ans++; j=lps[j-1];}
                else if(i<n && b.charAt(j)!=a.charAt(i))
                {
                    if(j!=0) j=lps[j-1];
                    else i++;
                }
            }
            return ans;
        }
    }

    static class nCr
    {
        long f[],in[],M;
        int N;
        nCr(int n,long mod)
        {
            N=n;
            f=new long[N+1];
            M=mod;
        }

        void factorials()
        {
            f[0]=1;
            int i;
            for(i=1;i<=N;i++) f[i]=f[i-1]*i%M;
            in[N]=power(f[N],M-2);
            for(i=N-1;i>0;i--) in[i]=in[i+1]*(i+1)%M;
        }

        long power(long a,long b)
        {
            long res=1;
            while(b!=0)
            {
                if(b%2==1) res=res*a%M;
                b>>=1;
                a=a*a%M;
            }
            return res;
        }

        long ncr(int n,int r)
        {
            if(r>n) return 0;
            return f[n]*in[r]%M*in[n-r]%M;
        }
    }

    static class ConvexHull
    {
        //can make this outside as well, when we have to take inputs
        class Point
        {
            long x,y;
            Point(long a,long b)
            {
                x=a; y=b;
            }
        }

        long distsq(Point a,Point b) //returns square of distance between 2 points
        {
            return (a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y);
        }

        int orientation(Point a,Point b,Point c) //returns orientation of 3 points
        {
            long val=a.x*b.y-a.y*b.x+b.x*c.y-b.y*c.x+c.x*a.y-c.y*a.x;
            if(val==0) return 0;
            return val>0?1:2;
        }

        Point nextToTop(Stack<Point> s)
        {
            Point cur=s.pop();
            Point res=s.peek();
            s.push(cur);
            return res;
        }

        ArrayList<Point> graham_scan(ArrayList<Point> al)    //find all convex hulls in the given set of points
        {
            int i,min=0;
            long ymin=al.get(0).y;
            for(i=1;i<al.size();i++)
            if(al.get(i).y<ymin || (al.get(i).y==ymin && al.get(i).x<al.get(min).x))
            {ymin=al.get(i).y; min=i;}

            Point t=al.get(min);
            Collections.sort(al,new Comparator<Point>(){
                public int compare(Point a,Point b)
                {
                    int o=orientation(a,t,b);
                    if(o==0) return distsq(t,b)>=distsq(t,a)?-1:1;
                    return o==2?-1:1;
                }});
            Point pn=al.get(al.size()-1);
            int idx=1,n=al.size();
            for(i=1;i<n;i++)    //equal points or on same line(need not be considered)
            //change this if the need arises
            {
                while(i<n-1 && orientation(t,al.get(i),al.get(i+1))==0) i++;
                al.set(idx++,al.get(i));
            }

            Stack<Point> s=new Stack<>();
            s.push(al.get(0)); s.push(al.get(1));
            if(idx>2) s.push(al.get(2));
            else return new ArrayList<>(s);
            for(i=3;i<idx;i++)  //gets all required points in anticlockwise direction
            {
                while(orientation(nextToTop(s),s.peek(),al.get(i))!=2) s.pop();
                s.push(al.get(i));
            }
            return new ArrayList<>(s);
        }

        boolean inside(ArrayList<Point> pgon,Point p)
        {
            //if point is inside polygon, cross product of point with all lines will be clockwise or counterclockwise
            int c=0,i=0,n=pgon.size(),o=orientation2(pgon.get(i),pgon.get(i+1),p);
            do
            {
                int next=(i+1)%n;
                if(orientation2(pgon.get(i),pgon.get(next),p)!=o) return false;
                i=next;
            }while(i!=0);
            return o!=0;
        }

        int orientation2(Point p, Point q, Point r)  //cross product of point with line
        {
            long val=(q.y-p.y)*(r.x-p.x)-(q.x-p.x)*(r.y-p.y);
            if(val==0) return 0;
            return (val>0)?1:2; // clock or counterclock wise
        }
    }
}
