package Infrean.Practice1.Hello.World.repository;

import Infrean.Practice1.Hello.World.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource; // DB 연동을위해 필요
    public JdbcMemberRepository(DataSource dataSource) { //스프링으로부터 주입받기 위해 생성자 필요
        this.dataSource = dataSource;
        //dataSource.getConnection()을 통해 DB와 연결되는 소켓을 얻을 수 있음
        // 하지만 계속 새로운 Connection이 주어지기 때문에 아래 private Connection getConnection을 이용함
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)"; //SQL문 생성, 파라미터 바인딩을 위해 물음표 처리
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //결과를 받음

        try {
            conn = getConnection(); //Connection을 가지고 와서
            pstmt = conn.prepareStatement(sql, // SQL을 넣음
                    Statement.RETURN_GENERATED_KEYS); // INSERT 할 때 사용되며, rs와 매칭됨
            pstmt.setString(1, member.getName()); // ?와 파라미터 인덱스와 매칭되며, member.getName()을 통해 값을 넣음
            pstmt.executeUpdate(); //DB에 실제 쿼리가 날아감
            rs = pstmt.getGeneratedKeys(); // 생성한 DB의 key에 맞는 값을 반환

            if (rs.next()) { //next()를 통해 rs의 다음값이 존재할 경우 실행
                member.setId(rs.getLong(1)); //값을 꺼내 세팅함
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); //아래 close 문 확인하기 (복잡)
        }
        //try~catch문 이후 자원 릴리즈가 필요함
        //DB Connection을 사용하면 외부 네트워크를 사용하기 때문에 끝나면 모두 끊어 릴리즈를 반환해야 함
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(); //Connection을 가지고 SQL문을 날리고
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id); //PreparedStatement 세팅을 함
            rs = pstmt.executeQuery();

            if(rs.next()) {//rs에서 읽어올 값이 있을 경우 아래 객체를 생성해 반환함
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
        //Spring을 통해 getConnection을 사용할 경우, dataSourceutils를 통해 Connection을 획득해야 함
        //그래야 이전 트랜잭션에 걸리면 같은 것을 유지할 수 있도록 해줌
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}