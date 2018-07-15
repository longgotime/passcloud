package com.paascloud.common.service;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileReqDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileRespDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * <p>Title: FileService . </p>
 * <p>文件接口  </p>
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018-07-15 下午4:50
 */
public interface FileService {
    /**
     * Upload file string.
     *
     * @param multipartRequest    the multipart request
     * @param optUploadFileReqDto the opt upload file req dto
     * @param loginAuthDto        the login auth dto
     * @param storeDbFlag         the store db flag
     *
     * @return the string
     */
    List<OptUploadFileRespDto> uploadFile(MultipartHttpServletRequest multipartRequest, OptUploadFileReqDto optUploadFileReqDto, LoginAuthDto loginAuthDto, boolean storeDbFlag);
}
