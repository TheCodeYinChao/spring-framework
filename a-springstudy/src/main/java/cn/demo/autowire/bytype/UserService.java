package cn.demo.autowire.bytype;


public class UserService {
	private UserDao userDao;

	public void queryByDao(){
		userDao.query();
	}
}
