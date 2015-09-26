package ch.ergon.eclipselinkexp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * You must create a table and a sequence on the oracle database
 *
 * create sequence SEQ_GEN_IDENTITY;
 *
 * create table TODO (
 * id number primary key,
 * SUMMARY varchar2(300),
 * DESCRIPTION VARCHAR2(300)
 * );
 */
public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "todos";
	private static EntityManagerFactory factory;

	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		//listTodos(em);
		//createNewTodo(em);
		//createNewTodo(em);
		for (int i = 0; i < 5; i++) {
			System.out.println(fetchTodo(em, i % 2 + 1));
		}

		em.close();
	}

	private static Todo fetchTodo(EntityManager em, long id) {
		return em.find(Todo.class, id);
	}

	@SuppressWarnings("unchecked")
	private static void listTodos(EntityManager em) {
		// read the existing entries and write to console
		Query q = em.createQuery("select t from Todo t");
		List<Todo> todoList = q.getResultList();
		for (Todo todo : todoList) {
			System.out.println(todo);
		}
		System.out.println("Size: " + todoList.size());
	}

	private static void createNewTodo(EntityManager em) {
		// create new todo
		em.getTransaction().begin();
		Todo todo = new Todo();
		todo.setSummary("This is a test");
		todo.setDescription("This is a test");
		em.persist(todo);
		em.getTransaction().commit();
	}
}
