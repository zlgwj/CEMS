package com.gwj.common.utils;

import cn.hutool.core.util.StrUtil;
import com.gwj.common.entity.Bbox;
import com.gwj.common.exception.MyException;
import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.decoder.*;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSGeoTIFFDatastoreEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
public class GeoserverUtil {

    private static String geoURL = "http://localhost:12345/geoserver";
    private static String username = "admin";
    private static String password = "geoserver";

    public static Boolean publishTif(File file, URL url, String workspace, String layerName, String style) {

        try {
            // 判断工作区（workspace）是否存在，不存在则创建
            URL u = new URL(geoURL);
            GeoServerRESTManager manager = new GeoServerRESTManager(u, username, password);

            GeoServerRESTPublisher publisher = manager.getPublisher();
            List<String> workspaces = manager.getReader().getWorkspaceNames();
            if (!workspaces.contains(workspace)) {
                boolean createws = publisher.createWorkspace(workspace);
                log.info("create workspace : " + createws);
            } else {
                log.info("workspace已经存在了,workSpace :{}", workspace);
            }
            // 判断数据存储（datastore）是否已经存在，不存在则创建;
//            image.setImgName(file.getName().substring(0 , file.getName().indexOf(".")));
//            image.setStoreName(file.getName().substring(0 , file.getName().indexOf(".")));
            String store_name = layerName; // 待创建和发布图层的数据存储名称store

            RESTDataStore restStore = manager.getReader().getDatastore(workspace, store_name);
            if (restStore == null) {
                GSGeoTIFFDatastoreEncoder gsGeoTIFFDatastoreEncoder = new GSGeoTIFFDatastoreEncoder(store_name);
                gsGeoTIFFDatastoreEncoder.setWorkspaceName(workspace);

                gsGeoTIFFDatastoreEncoder.setUrl(url);

                boolean createStore = manager.getStoreManager().create(workspace, gsGeoTIFFDatastoreEncoder);
                log.info("create store (TIFF文件创建状态) : {}", createStore);

//                publisher.publishGeoTIFF(workspace, store_name, layerName);

                if (StrUtil.isBlank(style)) {

                    return publisher.publishGeoTIFF(workspace, store_name, layerName, file, "EPSG:4326", GSResourceEncoder.ProjectionPolicy.FORCE_DECLARED, "", (double[])null);
                }
                return publisher.publishGeoTIFF(workspace, store_name, layerName, file, "EPSG:4326", GSResourceEncoder.ProjectionPolicy.FORCE_DECLARED, style, (double[])null);
//                RESTCoverageStore restCoverageStore = publisher.publishExternalGeoTIFF(workspace, layerName, file, coverageEncoder, layerEncoder);
//                return restCoverageStore != null;
//                System.out.println("publish (TIFF文件发布状态) : " + publish);
//                return publish;

            } else {
                log.info("数据存储已经存在了,store:" + store_name);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(e.getMessage());
        }
    }



    public static String previewUrl(String workspace, String layerName) throws MalformedURLException {
        GeoServerRESTManager manager = new GeoServerRESTManager(new URL(geoURL), username, password);
        RESTLayer layer = manager.getReader().getLayer(workspace, layerName);
        RESTResource resource = manager.getReader().getResource(layer);
        StringBuilder sb = new StringBuilder();
        sb.append(workspace);
        sb.append("/wms");
        sb.append("?service=WMS")
                .append("&version=1.1.0")
                .append("&request=getMap")
                .append("&layers=").append(workspace).append(":").append(layerName)
                .append("&bbox=").append(resource.getMinX()).append(",").append(resource.getMinY()).append(",").append(resource.getMaxX()).append(",")
                .append(resource.getMaxY())
                .append("&width=1000")
                .append("&height=1000")
                .append("&srs=EPSG:4326")
                .append("&format=image/png")
                .append("&styles=");
        return sb.toString();
    }


    public static Bbox getBbox(String workspace, String layerName) throws MalformedURLException {
        GeoServerRESTManager manager = new GeoServerRESTManager(new URL(geoURL), username, password);
        RESTLayer layer = manager.getReader().getLayer(workspace, layerName);
        RESTResource resource = manager.getReader().getResource(layer);
        Bbox bbox = new Bbox();
        bbox.setMaxX(resource.getMaxX());
        bbox.setMinX(resource.getMinX());
        bbox.setMaxY(resource.getMaxY());
        bbox.setMinY(resource.getMinY());
        return bbox;
    }

}
