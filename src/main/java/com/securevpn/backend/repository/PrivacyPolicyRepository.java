package com.securevpn.backend.repository;

import com.securevpn.backend.entity.PrivacyPolicyMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivacyPolicyRepository extends JpaRepository<PrivacyPolicyMeta, Long> {

    /** Returns the most recently updated policy entry */
    Optional<PrivacyPolicyMeta> findTopByOrderByUpdatedAtDesc();
}
