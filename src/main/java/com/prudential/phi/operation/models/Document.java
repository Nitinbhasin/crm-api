package com.prudential.phi.operation.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Document
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;
	@SerializedName("id")
	private String id = null;

	@SerializedName("type")
	private String type = null;

	@SerializedName("desc")
	private String desc = null;

	@SerializedName("comments")
	private String comments = null;

	@SerializedName("name")
	private String name = null;

	@SerializedName("filename")
	private String filename = null;

	@SerializedName("format")
	private String format = null;

	@SerializedName("contentType")
	private String contentType = null;

	@SerializedName("extension")
	private String extension = null;

	@SerializedName("compression")
	private String compression = null;

	@SerializedName("encryption")
	private String encryption = null;

	@SerializedName("isStatic")
	private Boolean isStatic = null;

	@SerializedName("isPublic")
	private Boolean isPublic = null;

	@SerializedName("tags")
	private Map<String, String> tags = null;

	@SerializedName("url")
	private String url = null;

	@SerializedName("content")
	private String content = null;

	@SerializedName("parts")
	private Map<String, Integer> parts = null;

	@SerializedName("version")
	private String version = null;

	@SerializedName("language")
	private String language = null;

	@SerializedName("category")
	private String category = null;

	@SerializedName("subCategory")
	private String subCategory = null;

	@SerializedName("parentId")
	private String parentId = null;

	@SerializedName("contentLength")
	private Long contentLength = null;

	@SerializedName("publishedDate")
	private String publishedDate = null;

	@SerializedName("published")
	private Boolean published = null;

	@SerializedName("updatedDate")
	private String updatedDate = null;


	public Document id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 * 
	 * @return id
	 **/
	@Schema(description = "")
	public String getId() {
		if (id.contains("{") || id.contains("}")) {
			return id.replace("{", "").replace("}", "");
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Document type(String type) {
		this.type = type;
		return this;
	}

	/**
	 * Get type
	 * 
	 * @return type
	 **/
	@Schema(description = "")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Document desc(String desc) {
		this.desc = desc;
		return this;
	}

	/**
	 * Get desc
	 * 
	 * @return desc
	 **/
	@Schema(description = "")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Document comments(String comments) {
		this.comments = comments;
		return this;
	}

	/**
	 * Get comments
	 * 
	 * @return comments
	 **/
	@Schema(description = "")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Document name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Get name
	 * 
	 * @return name
	 **/
	@Schema(description = "")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Document filename(String filename) {
		this.filename = filename;
		return this;
	}

	/**
	 * Get filename
	 * 
	 * @return filename
	 **/
	@Schema(description = "")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Document format(String format) {
		this.format = format;
		return this;
	}

	/**
	 * Get format
	 * 
	 * @return format
	 **/
	@Schema(description = "")
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Document contentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	/**
	 * Get contentType
	 * 
	 * @return contentType
	 **/
	@Schema(description = "")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Document extension(String extension) {
		this.extension = extension;
		return this;
	}

	/**
	 * Get extension
	 * 
	 * @return extension
	 **/
	@Schema(description = "")
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Document compression(String compression) {
		this.compression = compression;
		return this;
	}

	/**
	 * Get compression
	 * 
	 * @return compression
	 **/
	@Schema(description = "")
	public String getCompression() {
		return compression;
	}

	public void setCompression(String compression) {
		this.compression = compression;
	}

	public Document encryption(String encryption) {
		this.encryption = encryption;
		return this;
	}

	/**
	 * Get encryption
	 * 
	 * @return encryption
	 **/
	@Schema(description = "")
	public String getEncryption() {
		return encryption;
	}

	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	public Document isStatic(Boolean isStatic) {
		this.isStatic = isStatic;
		return this;
	}

	/**
	 * Get isStatic
	 * 
	 * @return isStatic
	 **/
	@Schema(description = "")
	public Boolean isIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Document isPublic(Boolean isPublic) {
		this.isPublic = isPublic;
		return this;
	}

	/**
	 * Get isPublic
	 * 
	 * @return isPublic
	 **/
	@Schema(description = "")
	public Boolean isIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Document tags(Map<String, String> tags) {
		this.tags = tags;
		return this;
	}

	public Document putTagsItem(String key, String tagsItem) {
		if (this.tags == null) {
			this.tags = new HashMap<String, String>();
		}
		this.tags.put(key, tagsItem);
		return this;
	}

	/**
	 * Get tags
	 * 
	 * @return tags
	 **/
	@Schema(description = "")
	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public Document url(String url) {
		this.url = url;
		return this;
	}

	/**
	 * Get url
	 * 
	 * @return url
	 **/
	@Schema(description = "")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Document content(String content) {
		this.content = content;
		return this;
	}

	/**
	 * Get content
	 * 
	 * @return content
	 **/
	@Schema(description = "")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Document parts(Map<String, Integer> parts) {
		this.parts = parts;
		return this;
	}

	public Document putPartsItem(String key, Integer partsItem) {
		if (this.parts == null) {
			this.parts = new HashMap<String, Integer>();
		}
		this.parts.put(key, partsItem);
		return this;
	}

	/**
	 * Get parts
	 * 
	 * @return parts
	 **/
	@Schema(description = "")
	public Map<String, Integer> getParts() {
		return parts;
	}

	public void setParts(Map<String, Integer> parts) {
		this.parts = parts;
	}

	public Document version(String version) {
		this.version = version;
		return this;
	}

	/**
	 * Get version
	 * 
	 * @return version
	 **/
	@Schema(description = "")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Document language(String language) {
		this.language = language;
		return this;
	}

	/**
	 * Get language
	 * 
	 * @return language
	 **/
	@Schema(description = "")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Document category(String category) {
		this.category = category;
		return this;
	}

	/**
	 * Get category
	 * 
	 * @return category
	 **/
	@Schema(description = "")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Document subCategory(String subCategory) {
		this.subCategory = subCategory;
		return this;
	}

	/**
	 * Get subCategory
	 * 
	 * @return subCategory
	 **/
	@Schema(description = "")
	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Document parentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	/**
	 * Get parentId
	 * 
	 * @return parentId
	 **/
	@Schema(description = "")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Document contentLength(Long contentLength) {
		this.contentLength = contentLength;
		return this;
	}

	/**
	 * Get contentLength
	 * 
	 * @return contentLength
	 **/
	@Schema(description = "")
	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * Get updateDated
	 * 
	 * @return updateDated
	 **/
	@Schema(example = "2024-10-07T06:56:27.345Z", description = "")
	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updateDated) {
		this.updatedDate = updateDated;
	}


	/**
	 * Get publishedDate
	 * 
	 * @return publishedDate
	 **/
	@Schema(example = "2024-10-07T06:56:27.345Z", description = "")
	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Document published(Boolean published) {
		this.published = published;
		return this;
	}

	/**
	 * Get published
	 * 
	 * @return published
	 **/
	@Schema(description = "")
	public Boolean isPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	

	

}
