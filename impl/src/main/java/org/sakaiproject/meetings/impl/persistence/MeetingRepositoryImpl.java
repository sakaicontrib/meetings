package org.sakaiproject.meetings.impl.persistence;

import org.sakaiproject.meetings.api.persistence.MeetingRepository;
import org.sakaiproject.serialization.BasicSerializableRepository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.sakaiproject.meetings.api.model.AttendeeType;
import org.sakaiproject.meetings.api.model.Meeting;
import org.sakaiproject.meetings.api.model.MeetingAttendee;

public class MeetingRepositoryImpl extends BasicSerializableRepository<Meeting, String> implements MeetingRepository {

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public Optional<Meeting> findById(String id) {
        Meeting meeting = (Meeting) startCriteriaQuery().add(Restrictions.eq("id", id)).uniqueResult();
        return Optional.ofNullable(meeting);
    }
    
    public Meeting findMeetingById(String id) {
        return (Meeting) startCriteriaQuery().add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void deleteById(String id) {
        getCurrentSession().createQuery("delete from Meeting where id = :id").setParameter("id", id).executeUpdate();
    }
    
    @Override
    public List<Meeting> getSiteMeetings(String siteId) {
        return getCurrentSession().createQuery("from Meeting where siteId = :siteId").setParameter("siteId", siteId).list();
    }
    
    @Override
    public List<Meeting> getMeetings(String userId, String siteId, List<String> groupIds) {
        return getCurrentSession().createQuery("select m from Meeting as m, MeetingAttendee as a where m.siteId = :siteId and a.meeting.id = m.id and "
                + "((a.type = 0 and a.objectId = :userId) or (a.type = 1 and a.objectId = :siteId) or (a.type = 3 and a.objectId in :groupIds))")
        .setParameter("siteId", siteId)
        .setParameter("userId", userId)
        .setParameter("groupIds", groupIds)
        .list();
    }
    
}
