% Matlab Benchmark Script
%
% This script runs a similar benchmark as in UJMP in Matlab, Octave,
% FreeMat or compatible software.
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

burninruns=3;
runs=25;
maxStd=10;
maxTrials=20;
maxTime=30000;
sizes=[2;3;4;5;10;20;50;100;200;500;1000;2000;5000];
sizes=[sizes,sizes];


burninruns=0;
runs=2;
maxStd=10;
maxTrials=1;
maxTime=1000;

clc;

mkdir('results');
mkdir('results/',version);

for task=1:10
    stopped=0;

    alltime=[];
    alldeltas=[];

    % chol not supported in FreeMat
    if(task==6 && strcmp(version,'4.0'))
       continue;
    end;
    if(task==6 && strcmp(version,'3.6'))
       continue;
    end;

    if(task==1)
        taskname='timesScalar';
    elseif(task==2)
        taskname='plusMatrix';
    elseif(task==3)
        taskname='transpose';
    elseif(task==4)
        taskname='mtimes';
    elseif(task==5)
        taskname='inv';
    elseif(task==6)
        taskname='invSPD';
    elseif(task==7)
        taskname='solveSquare';
    elseif(task==8)
        taskname='solveTall';
    elseif(task==9)
        taskname='svd';
    elseif(task==10)
        taskname='eig';
    end;

    for s=1:(size(sizes,1))

        cursize=sizes(s,:);
        bestStd=1e16;
        bestTime=[];

        for trial=1:maxTrials

            ts=zeros(runs,1);
            deltas=zeros(runs,1);

            fprintf('%s',taskname);
            fprintf(' [%dx%d] ',cursize(1),cursize(2));

            for i=1:burninruns
                if(stopped==0)
                    fprintf('#');
                    [t,delta]=benchmarktask(task,cursize);
                    if(isnan(t)||t*1000>maxTime)
                        stopped=1;
			t=NaN;
			delta=NaN;
			deltas(:,1)=NaN;
                    end;
                end;
            end;

            for i=1:runs
                if(stopped==0)
                    fprintf('.');
                    [t,delta]=benchmarktask(task,cursize);
                    if(isnan(t)||t*1000>maxTime)
                        stopped=1;
			ts(i,1)=NaN;
			delta=NaN;
                        deltas(:,1)=NaN;
                    else
                        ts(i,1)=t*1000;
                        deltas(i,1)=delta;
                    end;
                else
		    ts(i,1)=NaN;
		    delta=NaN;
		    deltas(:,1)=NaN;
		end;
            end;

            tempStd=std(ts)/mean(ts)*100;

            fprintf(' %f+-%f (+-%f%%)',mean(ts),std(ts),tempStd);

            if(~isnan(mean(delta)))
                fprintf(' delta:%e',mean(deltas));
            end;

            if(tempStd>maxStd)
                fprintf(' standard deviation too large, result discarded');
            end;
            
            if(sum(ts<0)~=0)
                fprintf(' time could not be measured, result discarded');
            end;

            if(isempty(bestTime))
                bestTime=ts;
            end;

            if (tempStd<bestStd&&sum(ts<0)==0)
                bestStd=tempStd;
                bestTime=ts;
            end;

            fprintf('\n');

            if(tempStd<maxStd&&sum(ts<0)==0)
                break;
            end;

        end;

        alltime=[alltime,bestTime];
        alldeltas=[alldeltas,delta];

    end;

    fid = fopen(['results/',version,'/',taskname,'.csv'],'w');
    fprintf(fid, ['%d',repmat('\t%d',1,size(alltime,2)-1),'\n'],sizes(:,1)');
    fprintf(fid, ['%8f',repmat('\t%8f',1,size(alltime,2)-1),'\n'],alltime');
    fclose(fid);

    if(~isnan(alldeltas))
        fid = fopen(['results/',version,'/',taskname,'-diff.csv'],'w');
        fprintf(fid, ['%d',repmat('\t%d',1,size(alldeltas,2)-1),'\n'],sizes(:,1)');
        fprintf(fid, ['%8e',repmat('\t%8e',1,size(alldeltas,2)-1),'\n'],alldeltas');
        fclose(fid);
    end;

end;
