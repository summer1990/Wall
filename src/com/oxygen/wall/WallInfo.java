package com.oxygen.wall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;

/**
 * @ClassName WallInfo
 * @Description 留言板信息类
 * @author oxygen
 * @email oxygen0106@163.com
 * @date 2014-8-18 下午4:30:32
 */
public class WallInfo implements Serializable {

	private static final long serialVersionUID = 1L;// 序列化ID

	private long wallId;// 留言板ID
	private String wallName;// 留言板名称
	private long ownerId;// 留言板创建者ID
	private LatLng location;// 坐标：经度+纬度
	private double latitude;// 纬度
	private double longitude;// 经度
	private int imgId;// 或 服务器端的图片URL： private String imgUrl;
	private int distance;//留言板距离
	private int commentCount;// 评论数量
	// private int zan;//赞

	/**
	 * Constructor
	 */
	public WallInfo() {

	}

	/**
	 * Constructor
	 * 
	 * @param wallId
	 * @param ownerId
	 * @param location
	 * @param distance
	 */
	public WallInfo(long wallId, long ownerId, LatLng location, int distance) {
		super();
		this.wallId = wallId;
		this.ownerId = ownerId;
		this.location = location;
		this.distance = distance;
	}

	/**
	 * Constructor
	 * 
	 * @param wallId
	 * @param ownerId
	 * @param location
	 * @param imgId
	 * @param distance
	 */
	public WallInfo(long wallId, long ownerId, LatLng location, int imgId,
			int distance) {
		super();
		this.wallId = wallId;
		this.ownerId = ownerId;
		this.location = location;
		this.imgId = imgId;
		this.distance = distance;
		this.distance = distance;
	}

	/**
	 * Constructor
	 * 
	 * @param wallId
	 * @param wallName
	 * @param ownerId
	 * @param location
	 * @param imgId
	 * @param distance
	 */
	public WallInfo(long wallId, String wallName, long ownerId,
			LatLng location, int imgId, int distance) {
		super();
		this.wallId = wallId;
		this.wallName = wallName;
		this.ownerId = ownerId;
		this.location = location;
		this.imgId = imgId;
		this.distance = distance;
	}

	/**
	 * Constructor
	 * 
	 * @param wallId
	 * @param wallName
	 * @param ownerId
	 * @param latitude
	 * @param longitude
	 * @param imgId
	 * @param distance
	 */
	public WallInfo(long wallId, String wallName, long ownerId,
			double latitude, double longitude, int imgId, int distance) {
		super();
		this.wallId = wallId;
		this.wallName = wallName;
		this.ownerId = ownerId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.distance = distance;
	}

	/**
	 * @param @param wallId
	 * @return void
	 * @Description 设置留言板ID
	 */
	public void setWallId(long wallId) {
		this.wallId = wallId;
	}

	/**
	 * @param @return
	 * @return long
	 * @Description 获取留言板ID
	 */
	public long getWallId() {
		return wallId;
	}

	/**
	 * @param @param ownerId
	 * @return void
	 * @Description 设置创建者Id
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @param @return
	 * @return long
	 * @Description 获取创建者ID
	 */
	public long getOwnerId() {
		return ownerId;
	}

	/**
	 * @param @param wallName
	 * @return void
	 * @Description 设置留言板名称
	 */
	public void setWallName(String wallName) {
		this.wallName = wallName;
	}

	/**
	 * @param @return
	 * @return String
	 * @Description 获取留言板名称
	 */
	public String getWallName() {
		return wallName;
	}

	/**
	 * @param @return
	 * @return double
	 * @Description 获取纬度
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param @param latitude
	 * @return void
	 * @Description 设置纬度
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param @return
	 * @return double
	 * @Description 获取经度
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param @param longitude
	 * @return void
	 * @Description 设置经度
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param @param location
	 * @return void
	 * @Description 设置坐标
	 */
	public void setlocation(LatLng location) {
		this.location = location;
	}

	/**
	 * @param @return
	 * @return LatLng
	 * @Description 获取坐标
	 */
	public LatLng getlocation() {
		return location;
	}

	/**
	 * @param @param imgId
	 * @return void
	 * @Description 设置图片ID
	 */
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	/**
	 * @param @return
	 * @return int
	 * @Description 获取图片ID
	 */
	public int getImgId() {
		return imgId;
	}

	/**
	 * @param @param distance
	 * @return void
	 * @Description 设置距离
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @param @return
	 * @return int
	 * @Description 获取距离
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param @param commentCount
	 * @return void
	 * @Description 设置评论数量
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * @param @return
	 * @return int
	 * @Description 获取评论数量
	 */
	public int getCommentCount() {
		return commentCount;
	}
}
