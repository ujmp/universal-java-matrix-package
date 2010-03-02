% Matlab Benchmark Task
%
% This function performs a single task such as matrix multiplication
% or singular value decomposition in Matlab, Octave, FreeMat or compatible
% software.
%
% Copyright (C) 2008-2010 by Holger Arndt
%
% This file is part of the Universal Java Matrix Package (UJMP).
% See the NOTICE file distributed with this work for additional
% information regarding copyright ownership and licensing.
%
% UJMP is free software; you can redistribute it and/or modify
% it under the terms of the GNU Lesser General Public License as
% published by the Free Software Foundation; either version 2
% of the License, or (at your option) any later version.
%
% UJMP is distributed in the hope that it will be useful,
% but WITHOUT ANY WARRANTY; without even the implied warranty of
% MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
% GNU Lesser General Public License for more details.
%
% You should have received a copy of the GNU Lesser General Public
% License along with UJMP; if not, write to the
% Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
% Boston, MA  02110-1301  USA
%

function [t,diff] = benchmarktask(number,cursize)

diff=NaN;

if(number==1)
    m1=rand(cursize)-0.5;
    tic;
    m2=m1*2.5;
    t=toc;
elseif(number==2)
    m1=rand(cursize)-0.5;
    m2=rand(cursize)-0.5;
    tic;
    m2=m1+m2;
    t=toc;
elseif(number==3)
    m1=rand(cursize)-0.5;
    tic;
    m2=m1';
    t=toc;
elseif(number==4)
    m1=rand(cursize)-0.5;
    m2=rand(cursize)-0.5;
    tic;
    m3=m1*m2;
    t=toc;
elseif(number==5)
    m1=rand(cursize)-0.5;
    tic;
    m2=inv(m1);
    t=toc;
    diff=norm(eye(cursize(1))-(m2*m1),'fro');
elseif(number==6)
    a=rand(cursize)-0.5;
    x=rand(cursize)-0.5;
    b=a*x;
    tic;
    x2=a\b;
    t=toc;
    diff=norm(x-x2,'fro');
elseif(number==7)
    cursize=[cursize(1)*2,cursize(2)];
    a=rand(cursize)-0.5;
    x=rand(cursize(2),cursize(1))-0.5;
    b=a*x;
    tic;
    x2=a\b;
    t=toc;
    diff=norm(x-x2,'fro');
elseif(number==8)
    a=rand(cursize)-0.5;
    tic;
    [u,s,v]=svd(a);
    t=toc;
    diff=norm(u*s*v'-a,'fro');
elseif(number==9)
    a=zeros(cursize);
    rows=size(a,1);
    cols=size(a,2);
    for r=1:rows
        for c=1:cols
            if(c<=r)
                f=rand-0.5;
                a(r,c)=f;
                a(c,r)=f;
            end;
        end;
    end;
    tic;
    [v,d]=eig(a);
    t=toc;
    diff=norm(v*d*v'-a,'fro');
else
    fprintf('unknown task\n');
    t=NaN;
end;
