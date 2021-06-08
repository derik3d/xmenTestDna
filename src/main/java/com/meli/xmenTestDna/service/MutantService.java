package com.meli.xmenTestDna.service;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class MutantService implements IMutantService{
	
	public static final Integer GOAL = 2;

	@Override
	public Boolean isMutant(String madeSequence, Integer N) {
		
		int mutations = 0;
		
		char[] charArray = madeSequence.toCharArray();

		mutations += testHorizontal(charArray,N,GOAL-mutations);
		mutations += testVertical(charArray,N,GOAL-mutations);
		mutations += testUpRightDiagonal(charArray,N,GOAL-mutations);
		mutations += testDownRightDiagonal(charArray,N,GOAL-mutations);
		
		return mutations > 1;
	}

	private Integer testHorizontal(char[] charArray, int N, int goal) {
		if(goal==0)return 0;
		BaseConsumer baseConsumer = new BaseConsumer(goal);
		for(int i=0; i<N ; i++) {
			for(int j=0; j<N; j++) {
				if(baseConsumer.goalMetWith(charArray[(i*N)+j]))
				return baseConsumer.getLinedCounter();
			}
			baseConsumer.clearLine();
		}
		return baseConsumer.getLinedCounter();
	}

	private Integer testVertical(char[] charArray, int N, int goal) {
		if(goal==0)return 0;
		BaseConsumer baseConsumer = new BaseConsumer(goal);
		for(int j=0; j<N ; j++) {
			for(int i=0; i<N; i++) {
				if(baseConsumer.goalMetWith(charArray[(i*N)+j]))
					return baseConsumer.getLinedCounter();
			}
			baseConsumer.clearLine();
		}
		return baseConsumer.getLinedCounter();
	}
	
	private Integer testUpRightDiagonal(char[] charArray, int N, int goal) {
		if(goal==0)return 0;
		BaseConsumer baseConsumer = new BaseConsumer(goal);

		int skipOffset = 0;
		for(int i=0; i<((2*N)-1) ; i++) {
			if(i>=N)
				skipOffset = (i-N) + 1;
			for(int j=skipOffset; j<(i+1) && j<N; j++) {
				if(baseConsumer.goalMetWith(charArray[((i-j)*N)+j]))
					return baseConsumer.getLinedCounter();
			}
			baseConsumer.clearLine();
		}
		
		return baseConsumer.getLinedCounter();
	}
	
	private Integer testDownRightDiagonal(char[] charArray, int N, int goal) {
		if(goal==0)return 0;
		BaseConsumer baseConsumer = new BaseConsumer(goal);

		int skipOffset = 0;
		for(int j=N-1; j>-N; j++) {
			if(j<0)
				skipOffset=-j;
			int tempJ = j+skipOffset;
			for(int i=skipOffset; i<N && tempJ<N; i++) {
				if(baseConsumer.goalMetWith(charArray[(i*N)+ tempJ++]))
				return baseConsumer.getLinedCounter();
			}
			baseConsumer.clearLine();
		}
		
		return baseConsumer.getLinedCounter();
	}
	

	static class BaseConsumer{
		
		Integer gotLineCounter = 0;
		Boolean currentGotLine = false;
		Integer QUEUE_LIMIT = 4;
		Integer goal;
		Queue<Character> queue = new LinkedList<Character>();
		
		public BaseConsumer(int goal) {
			this.goal = goal;
		}
		
		public void offer(char c){
			queue.add(c);

			if(queue.size()>QUEUE_LIMIT) {
				char removed = queue.poll();
				if(currentGotLine && c==removed) {
					return;
				}else {
					currentGotLine = false;
				}
			}
			
			if(queue.stream().allMatch(queue.peek()::equals)) {
				gotLineCounter++;
				currentGotLine = true;
			}
		}
		
		boolean goalMetWith(char c) {
			offer(c);
			return gotLineCounter>=goal;
		}
		
		public Integer getLinedCounter(){
			return gotLineCounter;
		}
		
		public void clearLine(){
			currentGotLine = false;
			queue = new LinkedList<Character>();
		}
	}
}
