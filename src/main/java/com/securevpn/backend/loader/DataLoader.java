package com.securevpn.backend.loader;

import com.securevpn.backend.entity.PrivacyPolicyMeta;
import com.securevpn.backend.entity.VpnServer;
import com.securevpn.backend.enums.ProtocolType;
import com.securevpn.backend.enums.ServerStatus;
import com.securevpn.backend.repository.PrivacyPolicyRepository;
import com.securevpn.backend.repository.VpnServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataLoader — seeds the database with sample VPN servers and privacy policy
 * on every application startup (for local H2 dev) or first run on production.
 *
 * These 9 servers match the Android app's sample data in ServerItem.getSampleServers()
 * so the frontend works end-to-end even before real VPN nodes are provisioned.
 *
 * Privacy note:
 *   Seed data contains only server infrastructure metadata.
 *   The host values are placeholders — replace with real server hostnames before deployment.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final VpnServerRepository serverRepository;
    private final PrivacyPolicyRepository policyRepository;

    @Override
    public void run(String... args) {
        seedServers();
        seedPrivacyPolicy();
    }

    // ─────────────────────────────────────────────
    // Seed VPN Servers
    // ─────────────────────────────────────────────

    private void seedServers() {
        if (serverRepository.count() > 0) {
            log.info("Servers already seeded — skipping.");
            return;
        }

        List<VpnServer> servers = List.of(

            // 1. United States — New York
            VpnServer.builder()
                .serverName("US-NY-01")
                .country("United States")
                .city("New York")
                .countryCode("US")
                .flagEmoji("🇺🇸")
                // TODO: Replace with real server hostname before deployment
                .host("us-ny-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(42)
                .loadPercent(35)
                .signalLevel(3)
                .isRecommended(true)
                .build(),

            // 2. Germany — Frankfurt
            VpnServer.builder()
                .serverName("DE-FR-01")
                .country("Germany")
                .city("Frankfurt")
                .countryCode("DE")
                .flagEmoji("🇩🇪")
                .host("de-fr-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(28)
                .loadPercent(22)
                .signalLevel(3)
                .isRecommended(true)
                .build(),

            // 3. Canada — Montreal
            VpnServer.builder()
                .serverName("CA-MT-01")
                .country("Canada")
                .city("Montreal")
                .countryCode("CA")
                .flagEmoji("🇨🇦")
                .host("ca-mt-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(55)
                .loadPercent(40)
                .signalLevel(2)
                .isRecommended(false)
                .build(),

            // 4. Netherlands — Amsterdam
            VpnServer.builder()
                .serverName("NL-AM-01")
                .country("Netherlands")
                .city("Amsterdam")
                .countryCode("NL")
                .flagEmoji("🇳🇱")
                .host("nl-am-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(35)
                .loadPercent(28)
                .signalLevel(3)
                .isRecommended(true)
                .build(),

            // 5. Singapore
            VpnServer.builder()
                .serverName("SG-01")
                .country("Singapore")
                .city("Singapore")
                .countryCode("SG")
                .flagEmoji("🇸🇬")
                .host("sg-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(88)
                .loadPercent(45)
                .signalLevel(2)
                .isRecommended(false)
                .build(),

            // 6. United Kingdom — London
            VpnServer.builder()
                .serverName("UK-LD-01")
                .country("United Kingdom")
                .city("London")
                .countryCode("GB")
                .flagEmoji("🇬🇧")
                .host("uk-ld-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(31)
                .loadPercent(30)
                .signalLevel(3)
                .isRecommended(true)
                .build(),

            // 7. France — Paris
            VpnServer.builder()
                .serverName("FR-PA-01")
                .country("France")
                .city("Paris")
                .countryCode("FR")
                .flagEmoji("🇫🇷")
                .host("fr-pa-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(38)
                .loadPercent(32)
                .signalLevel(2)
                .isRecommended(false)
                .build(),

            // 8. India — Mumbai
            VpnServer.builder()
                .serverName("IN-MU-01")
                .country("India")
                .city("Mumbai")
                .countryCode("IN")
                .flagEmoji("🇮🇳")
                .host("in-mu-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(72)
                .loadPercent(50)
                .signalLevel(2)
                .isRecommended(false)
                .build(),

            // 9. Japan — Tokyo
            VpnServer.builder()
                .serverName("JP-TK-01")
                .country("Japan")
                .city("Tokyo")
                .countryCode("JP")
                .flagEmoji("🇯🇵")
                .host("jp-tk-01.your-vpn-nodes.com")
                .port(51820)
                .protocol(ProtocolType.WIREGUARD)
                .isFree(true)
                .status(ServerStatus.ONLINE)
                .ping(95)
                .loadPercent(38)
                .signalLevel(1)
                .isRecommended(false)
                .build()
        );

        serverRepository.saveAll(servers);
        log.info("✅ Seeded {} VPN servers", servers.size());
    }

    // ─────────────────────────────────────────────
    // Seed Privacy Policy
    // ─────────────────────────────────────────────

    private void seedPrivacyPolicy() {
        if (policyRepository.count() > 0) {
            log.info("Privacy policy already seeded — skipping.");
            return;
        }

        PrivacyPolicyMeta policy = PrivacyPolicyMeta.builder()
                .version("1.0")
                // TODO: Replace with your real hosted privacy policy URL
                .policyUrl("https://your-domain.com/privacy-policy")
                .termsUrl("https://your-domain.com/terms")
                .summary(
                    "SecureVPN is a privacy-first free VPN service. " +
                    "We do not collect, log, or share your browsing activity, " +
                    "DNS queries, or traffic content. " +
                    "Only minimal session metadata is stored to operate the service."
                )
                .build();

        policyRepository.save(policy);
        log.info("✅ Seeded privacy policy metadata");
    }
}
