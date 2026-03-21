package com.securevpn.backend.service;

import com.securevpn.backend.dto.request.ConnectRequest;
import com.securevpn.backend.dto.request.DisconnectRequest;
import com.securevpn.backend.dto.response.BaseApiResponse;
import com.securevpn.backend.dto.response.ConnectData;

public interface VpnService {

    ConnectData connect(ConnectRequest request);

    BaseApiResponse<Void> disconnect(DisconnectRequest request);
}