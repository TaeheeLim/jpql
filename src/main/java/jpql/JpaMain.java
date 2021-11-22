package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            /*
                        for (int i = 0; i < 100; i++){
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();
            //페이징 처리 기본 쿼리
            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();
            System.out.println("result size = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }
            * */

                Team team = new Team();
                team.setName("teamA");
                em.persist(team);

                Member member = new Member();
                member.setUsername("member");
                member.setAge(10);

                member.setTeam(team);

                em.persist(member);

                em.flush();
                em.clear();

                String query = "select m from Member m left join m.team t on t.name = 'teamA'";
                List<Member> result = em.createQuery(query, Member.class)
                         .getResultList();

                tx.commit();

            //TypedQuery = 반환 타입이 명확 할 때
            //TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            //Query = 반환 타입이 명확하지 않을 때(useranme = String, age = int)
//            Query query3 = em.createQuery("select m.username, m.age from Member m");
            
            //값이 여러개 일때 getResultList, 한개일때는 getSingleResult.. getResultList가 없으면 null이 아닌 빈 리스트가 담김
            //getSingleResult는 많거나 없을 경우 Exception 발생
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();

        }

        emf.close();



    }
}
