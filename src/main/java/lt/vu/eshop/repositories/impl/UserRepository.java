package lt.vu.eshop.repositories.impl;

import lt.vu.eshop.entities.User;
import lt.vu.eshop.repositories.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class UserRepository extends Repository<User> {
    public User findByUsername(String username) {
        TypedQuery<User> query = getEm().createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);

        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }
}
