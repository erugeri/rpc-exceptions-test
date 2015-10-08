package com.test;

import java.util.UUID;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.googlecode.objectify.util.DAOBase;

public class GenerateCalls {
	
	private final static int NB_PUT = 10;
	private final static int NB_FIND = 1;
	private final static int NB_ADDED_TASKS = 30;
	
	public static String generate() {

		long nbEntitiesInDatastore = countEntities();
		long writtenEntitiesTime = putEntities();
		long readEntitiesTime = readEntities();
		addTasks();

		StringBuilder sb = new StringBuilder();
		sb.append(nbEntitiesInDatastore+" entities in table");
		sb.append("<br/>");
		sb.append(NB_PUT+" puts - "+writtenEntitiesTime+" ms");
		sb.append("<br/>");
		sb.append(NB_FIND+" reads - "+readEntitiesTime+" ms");
		sb.append("<br/>");
		sb.append(NB_ADDED_TASKS+" addedTasks");
		return sb.toString();
	}

	private static int countEntities() {
		return new DAOBase().ofy().query(Entity.class).count();
	}

	private static long putEntities() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < NB_PUT; i++) {
			Entity entity = new Entity();
			entity.setName(UUID.randomUUID().toString());
			new DAOBase().ofy().put(entity);
		}
		return System.currentTimeMillis()-start;
	}
	
	private static long readEntities() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < NB_FIND; i++) {
			new DAOBase().ofy().query(Entity.class)
					.prefetchSize(1000)
					.chunkSize(1000)
					.list();
		}
		return System.currentTimeMillis()-start;
	}

	private static void addTasks() {
		for (int i = 0; i < NB_ADDED_TASKS; i++) {
			Queue queue = QueueFactory.getQueue("test");
			queue.add(TaskOptions.Builder.withUrl("/task.jsp").method(Method.GET));
		}
	}
}
