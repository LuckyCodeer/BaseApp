package com.huodada.lib_common.http.parser;

import com.huodada.lib_common.entity.BaseResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.TypeParser;
import rxhttp.wrapper.utils.Converter;

/**
 * 接口响应数据解析
 * created by yhw
 * date 2022/11/8
 */
@Parser(name = "Response")
public class ResponseParser<T> extends TypeParser<T> {

    /**
     * 此构造方法适用于任意Class对象，但更多用于带泛型的Class对象，如：List<Student>
     * 用法:
     * Java: .asParser(new ResponseParser<List<Student>>(){})
     * Kotlin: .asParser(object : ResponseParser<List<Student>>() {})
     * 注：此构造方法一定要用protected关键字修饰，否则调用此构造方法将拿不到泛型类型
     */
    protected ResponseParser() {
        super();
    }

    /**
     * 此构造方法仅适用于不带泛型的Class对象，如: Student.class
     * 用法
     * Java: .asParser(new ResponseParser<>(Student.class))   或者  .asResponse(Student.class)
     * Kotlin: .asParser(ResponseParser(Student::class.java)) 或者  .asResponse(Student::class.java)
     */
    public ResponseParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(Response response) throws IOException {
        //HTTP的成功状态
        if (response.isSuccessful()) {
            BaseResponse<T> data = Converter.convertTo(response, BaseResponse.class, types);
            T t = data.getData(); //获取data字段
            if (t == null && types[0] == String.class) {
                /*
                 * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
                 * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
                 * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
                 */
                t = (T) data.getErrorMsg();
            }
            if (data.getErrorCode() != 0 || t == null) {//code不等于0，说明数据不正确，抛出异常
                throw new ParseException(String.valueOf(data.getErrorCode()), data.getErrorMsg(), response);
            }
            return t;
        }
        //HTTP的错误
        String errorMsg = "";
        if (response.code() == 404) {
            errorMsg = "接口地址错误";
        } else if (response.code() >= 500) {
            errorMsg = "请求服务器发生错误，请稍后";
        }
        throw new ParseException(String.valueOf(response.code()), errorMsg, response);
    }
}
