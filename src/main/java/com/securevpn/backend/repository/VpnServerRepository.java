package com.securevpn.backend.repository;

import com.securevpn.backend.entity.VpnServer;
import com.securevpn.backend.enums.ServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for VPN server data.
 */
@Repository
public interface VpnServerRepository extends JpaRepository<VpnServer, Long> {

    /**
     * Returns all free ONLINE servers sorted by ping ascending (fastest first).
     */
    List<VpnServer> findByIsFreeAndStatusOrderByPingAsc(Boolean isFree, ServerStatus status);

    /**
     * Returns all free servers regardless of status, sorted by status priority then ping.
     */
    @Query("SELECT s FROM VpnServer s WHERE s.isFree = true " +
           "ORDER BY CASE s.status " +
           "WHEN com.securevpn.backend.enums.ServerStatus.ONLINE THEN 1 " +
           "WHEN com.securevpn.backend.enums.ServerStatus.MAINTENANCE THEN 2 " +
           "ELSE 3 END, s.ping ASC")
    List<VpnServer> findAllFreeServersSorted();

    List<VpnServer> findByStatus(ServerStatus status);
}
