-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: netty_chat
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES (1,'/chatOfflineMessage',0,'离线消息',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(2,'/chatGroupUser',0,'群聊成员管理',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(3,'/sysMenu',0,'菜单',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(4,'/server',0,'服务端管理',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(5,'/sysRolePermission',0,'角色权限',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(6,'/chatGroup',0,'群聊管理',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(7,'/sysPermission',0,'权限',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(8,'/chatUserLink',0,'用户关系',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(9,'/sysLog',0,'日志',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(10,'/sysUser',0,'用户',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(11,'/sysRole',0,'角色',NULL,0,'2024-11-23 16:47:00','2024-11-23 16:47:00'),(12,'/sysUser/update',1,'修改用户',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(13,'/sysRole/updateRole',1,'修改角色',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(14,'/sysMenu/updateMenu',1,'修改菜单',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(15,'/chatUserLink/updateChatUserLink',1,'修改用户关系',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(16,'/chatGroup/updateChatGroup',1,'修改群聊',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(17,'/sysUser/login',1,'登录',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(18,'/sysUser/add',1,'添加用户',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(19,'/sysRolePermission/edit',1,'修改角色对应的权限',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(20,'/sysRole/searchQuery',1,'角色搜索分页',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(21,'/sysRole/addRole',1,'添加角色',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(22,'/sysPermission/list',1,'No summary',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(23,'/sysPermission/add',1,'添加权限',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(24,'/sysMenu/addMenu',1,'添加菜单',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(25,'/sysLog/searchQuery',1,'分页搜索',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(26,'/chatGroupUser/addGroupUser',1,'将某个成员加入群聊',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(27,'/chatGroup/addChatGroup',1,'创建群聊',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(28,'/sysPermission/getTree',1,'获得菜单树',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(29,'/sysPermission/getSon',1,'获得子节点',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(30,'/sysPermission/getRootMenu',1,'获得根节点',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(31,'/sysMenu/getSonMenu',1,'获得子节点',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(32,'/sysMenu/getRootMenu',1,'获得根节点',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(33,'/sysMenu/getMenuTree',1,'获得菜单树',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(34,'/server/count',1,'查看在线人数',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(35,'/chatUserLink/getChatUserLink',1,'根据用户 id 获取某个用户的好友',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(36,'/chatUserLink/addChatUserLink',1,'根据用户 id 添加某个用户为好友',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(37,'/chatOfflineMessage/getChatUserLink',1,'根据用户 id 获取某个用户离线消息',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(38,'/chatGroupUser/getGroupUser',1,'根据群聊 id查询群聊成员',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(39,'/chatGroup/getGroupByUserID',1,'根据 用户id 查询某个用户的所有群聊',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(40,'/sysRole/deletedRole',1,'删除角色',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(41,'/sysMenu/deletedMenu',1,'删除菜单',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(42,'/chatUserLink/deleteChatUserLink',1,'删除用户关系',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(43,'/chatGroupUser/delGroupUser',1,'将某个成员移除群聊',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(44,'/chatGroup/deleteChatGroup',1,'删除群聊',NULL,0,'2024-11-23 16:47:01','2024-11-23 16:47:01'),(45,'/server/view',1,'查看在线人数',NULL,0,'2024-12-08 21:42:32','2024-12-08 21:42:32');
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-12  7:53:29
