#rat in a maze
def maze(a,x,y):
    n=len(a)
    m=len(a[0])
    b=ord('#')
    d=[[0]*m for _ in range(n)]
    vis=[[False]*m for _ in range(n)]
    move=[[0,1],[1,0],[-1,0],[0,-1]]

    qx=[]
    qy=[]
    qx.append(0)
    qy.append(0)
    vis[0][0]=True
    while len(qx)!=0:
        px=qx.pop(0)
        py=qy.pop(0)
        #print(px,py,d[px][py])
        if px==x and py==y:
            #print(px,py)
            break
        c=0
        for i in move:
            tx=px+i[0]
            ty=py+i[1]
            #print(tx,ty)
            if tx<0 or tx>=n or ty<0 or ty>=m: continue
            if vis[tx][ty]==True or ord(a[tx][ty])==b: continue
            vis[tx][ty]=True
            d[tx][ty]=d[px][py]+1
            qx.append(tx)
            qy.append(ty)
            #print(tx,ty,d[tx][ty])
            c+=1
        #print(c)

    if vis[x][y]==False: d[x][y]=-1
    return d[x][y]

if __name__=='__main__':
    n,m=list(map(int,input().split()))
    a=[[None]*m for _ in range(n)]
    for i in range(n):
        s=input()
        for j in range(m):
            a[i][j]=s[j]
    x,y=list(map(int,input().split()))
    x-=1
    y-=1
    print(maze(a,x,y))



'''
#1
i/p-
4 4
..##
#...
..#.
#...
4 3

o/p-
5

#2
i/p-
10 10
..##...#..
.######...
.##..###..
...#.###..
#.....####
#..#.....#
##..###...
######....
##.....##.
....#..#.#
7 8

o/p-
13
'''