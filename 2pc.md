# 작업

* 2pc를 사용한 dual write를 통해 mysql과 postgresql에 쓰기작업을 한다.
* rollback 테스트

# 이슈

* postgresql에서 2pc를 사용하려면 prepare 단계에서 사용되는 prepared
  transaction이 존재해야한다. 
  다음 명령어를 통해 prepared transaction이 0보다 큰지 확인하고 postgresql.conf에서 해당 옵션값을 설정한다.
    ```sql
    SHOW max_prepared_transactions;
    ```