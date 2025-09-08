USE yunto;

-- 插入20条示例用户数据
INSERT INTO user (userAccount, userPassword, userName, userAvatar, userProfile, userRole) VALUES
                                                                                              ('alice2024', '25d55ad283aa400af464c76d713c07ad', 'Alice', 'https://placekitten.com/200/300', '热爱编程和摄影', 'user'),
                                                                                              ('bob_master', '25d55ad283aa400af464c76d713c07ad', 'Bob', NULL, '前端开发工程师', 'user'),
                                                                                              ('charlie_tech', '25d55ad283aa400af464c76d713c07ad', 'Charlie', 'https://via.placeholder.com/200x300', NULL, 'admin'),
                                                                                              ('diana_dev', '25d55ad283aa400af464c76d713c07ad', 'Diana', 'https://placekitten.com/202/302', '全栈开发者', 'user'),
                                                                                              ('evan_cloud', '25d55ad283aa400af464c76d713c07ad', 'Evan', NULL, '云计算专家', 'user'),
                                                                                              ('fiona_design', '25d55ad283aa400af464c76d713c07ad', 'Fiona', 'https://via.placeholder.com/204x304', 'UI/UX设计师', 'user'),
                                                                                              ('george_data', '25d55ad283aa400af464c76d713c07ad', 'George', 'https://placekitten.com/206/306', '数据分析师', 'user'),
                                                                                              ('hannah_ai', '25d55ad283aa400af464c76d713c07ad', 'Hannah', NULL, 'AI研究员', 'admin'),
                                                                                              ('ian_mobile', '25d55ad283aa400af464c76d713c07ad', 'Ian', 'https://via.placeholder.com/208x308', '移动开发工程师', 'user'),
                                                                                              ('julia_sec', '25d55ad283aa400af464c76d713c07ad', 'Julia', 'https://placekitten.com/210/310', '网络安全工程师', 'user'),
                                                                                              ('kevin_test', '25d55ad283aa400af464c76d713c07ad', 'Kevin', NULL, '软件测试工程师', 'user'),
                                                                                              ('lina_db', '25d55ad283aa400af464c76d713c07ad', 'Lina', 'https://via.placeholder.com/212x312', '数据库管理员', 'user'),
                                                                                              ('mike_ops', '25d55ad283aa400af464c76d713c07ad', 'Mike', 'https://placekitten.com/214/314', '运维工程师', 'user'),
                                                                                              ('nina_prod', '25d55ad283aa400af464c76d713c07ad', 'Nina', NULL, '产品经理', 'user'),
                                                                                              ('oscar_support', '25d55ad283aa400af464c76d713c07ad', 'Oscar', 'https://via.placeholder.com/216x316', '技术支持专员', 'user'),
                                                                                              ('paul_marketing', '25d55ad283aa400af464c76d713c07ad', 'Paul', 'https://placekitten.com/218/318', '数字营销专家', 'user'),
                                                                                              ('quincy_content', '25d55ad283aa400af464c76d713c07ad', 'Quincy', NULL, '内容创作者', 'user'),
                                                                                              ('rachel_hr', '25d55ad283aa400af464c76d713c07ad', 'Rachel', 'https://via.placeholder.com/220x320', '人力资源经理', 'user'),
                                                                                              ('sam_founder', '25d55ad283aa400af464c76d713c07ad', 'Sam', 'https://placekitten.com/222/322', '创业公司创始人', 'admin'),
                                                                                              ('tina_consult', '25d55ad283aa400af464c76d713c07ad', 'Tina', NULL, '技术顾问', 'user');