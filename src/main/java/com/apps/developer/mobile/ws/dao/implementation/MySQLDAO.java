package com.apps.developer.mobile.ws.dao.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.apps.developer.mobile.ws.dao.DAO;
import com.apps.developer.mobile.ws.dto.UserDto;
import com.apps.developer.mobile.ws.entity.UserEntity;
import com.apps.developer.mobile.ws.utils.HibernateUtils;

public class MySQLDAO implements DAO {
	Session session;

	public void openConnection() {
		SessionFactory sessionFactory = HibernateUtils.gestSessionFactory();
		session = sessionFactory.openSession();

	}

	public UserDto getUserByUserName(String userName) {

		UserDto userDto = null;
		CriteriaBuilder cb = session.getCriteriaBuilder();

		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("email"), userName));

		Query<UserEntity> query = session.createQuery(criteria);
		List<UserEntity> resultList = query.getResultList();

		if (resultList != null && resultList.size() > 0) {

			UserEntity userEntity = resultList.get(0);
			userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);

		}

		return userDto;
	}

	public UserDto getUser(String id) {

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);

		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("userId"), id));

		UserEntity userEntity = session.createQuery(criteria).getSingleResult();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;

	}

	public UserDto saveUser(UserDto user) {

		UserDto returnValue = null;
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		session.beginTransaction();
		session.save(userEntity);
		session.getTransaction().commit();

		returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	public void closeConnection() {
		if (session != null) {

			session.close();

		}

	}

	public void updateUser(UserDto userProfile) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userProfile, userEntity);
		session.beginTransaction();
		session.update(userEntity);
		session.getTransaction().commit();

	}

	@Override
	public List<UserDto> getUsers(int start, int limit) {

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create Criteria against a particular persistent class
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query roots always reference entities
		Root<UserEntity> userRoot = criteria.from(UserEntity.class);
		criteria.select(userRoot);

		// Fetch results from start to a number of "limit"
		List<UserEntity> searchResults = session.createQuery(criteria).setFirstResult(start).setMaxResults(limit)
				.getResultList();

		List<UserDto> returnValue = new ArrayList<UserDto>();
		for (UserEntity userEntity : searchResults) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

}
