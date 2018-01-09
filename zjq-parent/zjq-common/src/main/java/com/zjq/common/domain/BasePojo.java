package com.zjq.common.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.zjq.common.service.IErrorMessage;

public class BasePojo implements Serializable {

  private static final long serialVersionUID = -2554218714794202323L;

  protected Integer version;

  protected Timestamp lastModifiedDtm;

  protected Integer lastModifiedBy;

  protected Timestamp createdDtm;

  protected Integer createdBy = 0;

  protected Boolean deleted;

  protected Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BasePojo() {
    super();
    createdDtm = new Timestamp(new Date().getTime());
    lastModifiedDtm = createdDtm;
    deleted = false;
    version = 0;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public Timestamp getLastModifiedDtm() {
    return lastModifiedDtm;
  }

  public void setLastModifiedDtm(Timestamp lastModifiedDtm) {
    this.lastModifiedDtm = lastModifiedDtm;
  }

  public Integer getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(Integer lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Timestamp getCreatedDtm() {
    return createdDtm;
  }

  public void setCreatedDtm(Timestamp createdDtm) {
    this.createdDtm = createdDtm;
  }

  public void updateCommonInfo(Integer userId) {

    Timestamp now = new Timestamp(new Date().getTime());
    if (this.getId() == null || this.getId() == 0) { // New PoJo
      this.setVersion(0);
      if (this.getCreatedDtm() == null) {
        this.setCreatedDtm(now);
      }
      if (this.getCreatedBy() == null || this.getCreatedBy().equals(0)) {
        this.setCreatedBy(userId == null ? 0 : userId);
      }
      this.setLastModifiedDtm(now);
      this.setLastModifiedBy(userId == null ? 0 : userId);
      this.setDeleted(false);

    } else { // Update this
      this.setVersion(this.getVersion() == null ? 1 : this.getVersion() + 1);
      this.setLastModifiedDtm(now);
      this.setLastModifiedBy(userId == null ? 0 : userId);
    }
  }

  /**
   * Valid the version has been passed in correctly or not.
   * 
   * @return
   */
  public String validVersionForUpdate() {
    String ret = "";

    if (this.getVersion() == null && this.getVersion().intValue() > 0) {
      // Do nonthing
    } else {
      ret = IErrorMessage.MSG_100001;
    }

    return ret;
  }
  
  public Boolean validateCommonFields(){
	  Boolean result =true;
	  
	  if(version==null){
		  result =false;
	  }
	  
	  if(createdDtm==null){
		  result =false;
	  }
	  
	  if(deleted == null){
		  result =false;
	  }
	  
	  return result;
  }


  /**
   * 返回该对象的字符串表示(类似数组的toString方法输出结果)
   */
  // @Override
  // public String toString() {
  //
  // // 当前类反射方法组
  // Method[] methodGroup = this.getClass().getMethods();
  // String className = this.getClass().getName();
  // // 保存内容
  // StringBuffer content = new StringBuffer(className.substring(className.lastIndexOf(".")+1)+ " [");
  // // 保存属性的getter方法组
  // List<Method> getMethodGroup = new Vector<Method>();
  //
  // try {
  // // 遍历反射方法组并提取属性的getter方法
  // for (Method method : methodGroup) {
  // // 过滤与属性无关的get方法
  // if (method.getName().startsWith("get")
  // && !method.getName().equals("getClass")) {
  // // 保存属性的getter方法
  // Class[] c = method.getParameterTypes();
  // if(c == null || c.length == 0) {
  // getMethodGroup.add(method);
  // }
  // }
  // }
  // // 处理仅包含属性的getter方法
  // for (int i = 0; i < getMethodGroup.size(); i++) {
  // // 执行get方法并拼接获取到的返回值(如果底层方法返回类型为 void，则该调用返回 null)
  // content.append(getMethodGroup.get(i).invoke(this)
  // + ((i < getMethodGroup.size() - 1) ? ",\u0020" : "]"));
  // }
  // } catch (IllegalAccessException ex) {
  // System.err.println("BasePojo异常信息：参数错误，对象定义无法访问，无法反射性地创建一个实例！\r\n" + ex.getMessage());
  // } catch (IllegalArgumentException ex) {
  // System.err.println("BasePojo异常信息：参数错误，向方法传递了一个不合法或不正确的参数！\r\n" + ex.getMessage());
  // } catch (InvocationTargetException ex) {
  // System.err.println("BasePojo异常信息：参数错误，由调用方法或构造方法所抛出异常的经过检查的异常！\r\n" + ex.getMessage());
  // }
  //
  // // 返回结果
  // return content.toString();
  // }

}
