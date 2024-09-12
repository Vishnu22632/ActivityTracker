package com.synergytech.tms.repository;

import com.synergytech.tms.model.User;
import com.synergytech.tms.utils.PasswordUtil;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class UserRepository extends BaseRepository<User, Long> {

    public UserRepository() {
        super(User.class);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot)
          .where(cb.equal(userRoot.get("email"), email));
        TypedQuery<User> query = entityManager.createQuery(cq);
        try {
            User user = query.getSingleResult();
            if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                return user;
            }
        } catch (NoResultException e) {
            // No user found
        }
        return null;
    }
}
