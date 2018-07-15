package com.paascloud.common.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.paascloud.CheckFileUtil;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.common.service.FileService;
import com.paascloud.provider.model.dto.attachment.OptUploadFileByteInfoReqDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileReqDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileRespDto;
import com.paascloud.provider.service.OpcOssFeignApi;
import com.paascloud.wrapper.Wrapper;
import com.xiaoleilu.hutool.io.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>Title: FileServiceImpl . </p>
 * <p>文件接口  </p>
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018-07-15 下午4:51
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private OpcOssFeignApi opcOssFeignApi;

    @Override
    public List<OptUploadFileRespDto> uploadFile(MultipartHttpServletRequest multipartRequest, OptUploadFileReqDto optUploadFileReqDto, LoginAuthDto loginAuthDto, boolean storeDbFlag) {
        String fileType = optUploadFileReqDto.getFileType();
        String filePath = optUploadFileReqDto.getFilePath();
        optUploadFileReqDto.setStoreDb(storeDbFlag);
        List<OptUploadFileRespDto> result = Lists.newArrayList();
        if (PublicUtil.isEmpty(filePath)) {
            optUploadFileReqDto.setFilePath(GlobalConstant.Sys.DEFAULT_FILE_PATH);
        }
        try {
            List<MultipartFile> fileList = multipartRequest.getFiles("file");
            if (fileList.isEmpty()) {
                return result;
            }

            for (MultipartFile multipartFile : fileList) {
                String fileName = multipartFile.getOriginalFilename();
                if (PublicUtil.isEmpty(fileName)) {
                    continue;
                }
                Preconditions.checkArgument(multipartFile.getSize() <= GlobalConstant.FILE_MAX_SIZE, "上传文件不能大于5M");
                InputStream inputStream = multipartFile.getInputStream();

                String inputStreamFileType = FileTypeUtil.getType(inputStream);
                CheckFileUtil.checkFileType(fileType, inputStreamFileType);
                OptUploadFileByteInfoReqDto infoReqDto = new OptUploadFileByteInfoReqDto();
                infoReqDto.setFileByteArray(multipartFile.getBytes());
                infoReqDto.setFileName(fileName);
                optUploadFileReqDto.setUploadFileByteInfoReqDto(infoReqDto);
                Wrapper<OptUploadFileRespDto> uploadFileRespDtoWrapper = opcOssFeignApi.uploadFile(optUploadFileReqDto);
                OptUploadFileRespDto fileInfo = uploadFileRespDtoWrapper.getResult();
                if (fileInfo != null) {
                    result.add(fileInfo);
                }
            }
        } catch (IOException e) {
            log.error("上传文件失败={}", e.getMessage(), e);
        }
        return result;
    }
}
