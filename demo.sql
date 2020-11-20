SELECT
    zzz.*
FROM
    (
        SELECT
            m.id,
            m.username,
            b.arrears,
            (
                SELECT
                    (
                        SELECT
                            realname
                        FROM
                            t_s_base_user
                        WHERE
                            ID = a.e_id
                            AND sys_Company_Code LIKE 'A76%'
                    ) realname
                FROM
                    (
                        SELECT
                            COUNT(e_id) num,
                            e_id,
                            u_id
                        FROM
                            k_s_service
                        WHERE
                            sys_Company_Code LIKE 'A76%'
                        GROUP BY
                            e_id,
                            u_id
                        ORDER BY
                            num DESC
                    ) a
                WHERE
                    a.u_id = m.id
                ORDER BY
                    num DESC
                LIMIT
                    1
            ) realname, DATE_FORMAT(m.update_date, '%Y-%m-%d') AS update_date,
            m.age,
            m.phone,
            m.calendar,
            IF (
                m.YEAR IS NULL
                OR m.YEAR = '',
                IF (
                    m.MONTH IS NULL
                    OR m.MONTH = '',
                    NULL,
                    CONCAT(m.MONTH, '-', m.DAY)
                ),
                CONCAT(m.YEAR, '-', m.MONTH, '-', m.DAY)
            ) birthday,
            IF (
                m.YEAR = DATE_FORMAT(NOW(), '%y'),
                CONCAT(m.YEAR, '-', m.MONTH, '-', m.DAY),
                CONCAT(
                    DATE_FORMAT(now(), '%Y'),
                    '-',
                    m.MONTH,
                    '-',
                    m.DAY
                )
            ) ssr,
            (
                SELECT
                    COUNT(s.id)
                FROM
                    k_s_service s
                WHERE
                    s.u_id = m.id
            ) server_num,
            m.openid,
            IFNULL(aa.noCZ_totalpay, 0) - IFNULL(z.price, 0) noCZ_totalpay,
            (
                SELECT
                    departname
                FROM
                    t_s_depart
                WHERE
                    ID = m.depart_name
            ) departname,
            m.sex,
            m.depart_name,
            m.physiological_start,
            m.physiological_end,
            (
                SELECT
                    ul.level_name
                FROM
                    k_s_usercard_level ul
                WHERE
                    ul.sys_Company_Code = SUBSTR(m.sys_Company_Code, 1, 3)
                    AND m.noCZ_totalpay BETWEEN ul.start_price
                    AND ul.end_price
            ) LEVEL,
            (
                SELECT
                    IFNULL(SUM(vvv.FZ), 0)
                FROM
                    (
                        SELECT
                            M.FZ,
                            M.member_id
                        FROM
                            (
                                SELECT
                                    rc.buy_num,
                                    rc.state,
                                    po.id AS orderId,
                                    po.order_code,
                                    ct.card_type,
                                    (
                                        IF (
                                            rc.state = 3,
                                            po.price,
                                            FLOOR(
                                                po.price / rc.buy_num / (ct.use_time * 30.5) * (
                                                    timestampdiff(DAY, now(), rc.end_time)
                                                )
                                            )
                                        )
                                    ) AS FZ,
                                    rc.residue_num,
                                    DATE_FORMAT(rc.end_time, '%Y-%m-%d %H:%i:%S') AS end_time,
                                    rc.member_id
                                FROM
                                    k_s_residual_card rc
                                    LEFT JOIN k_s_pro_order po ON rc.order_id = po.id
                                    LEFT JOIN k_s_card_type ct ON rc.card_id = ct.id
                                WHERE
                                    rc.state <> 0
                                    AND po.sys_Company_Code LIKE 'A76%'
                            ) M
                        WHERE
                            (
                                M.end_time > now()
                                OR M.state = 3
                            )
                            AND M.card_type = 7
                        UNION
                        ALL
                        SELECT
                            M.FZ,
                            M.member_id
                        FROM
                            (
                                SELECT
                                    rc.buy_num,
                                    rc.state,
                                    po.id AS orderId,
                                    po.order_code,
                                    ct.card_type,
                                    IF (
                                        rc.state = 3,
                                        po.price,
                                        (
                                            FLOOR(
                                                po.price / rc.buy_num / ct.number * rc.residue_num
                                            )
                                        )
                                    ) AS FZ,
                                    rc.residue_num,
                                    DATE_FORMAT(rc.end_time, '%Y-%m-%d %H:%i:%S') AS end_time,
                                    rc.member_id
                                FROM
                                    k_s_residual_card rc
                                    LEFT JOIN k_s_pro_order po ON rc.order_id = po.id
                                    LEFT JOIN k_s_card_type ct ON rc.card_id = ct.id
                                WHERE
                                    rc.state <> 0
                                    AND po.sys_Company_Code LIKE 'A76%'
                            ) M
                        WHERE
                            M.residue_num > 0
                            AND M.card_type = 8
                        UNION
                        ALL
                        SELECT
                            M.FZ,
                            M.member_id
                        FROM
                            (
                                SELECT
                                    rc.buy_num,
                                    rc.state,
                                    po.id AS orderId,
                                    po.order_code,
                                    ct.card_type,
                                    IF (
                                        rc.state = 3,
                                        po.price,
                                        (
                                            FLOOR(
                                                po.price / rc.buy_num / ct.number * rc.residue_num
                                            )
                                        )
                                    ) AS FZ,
                                    rc.residue_num,
                                    DATE_FORMAT(rc.end_time, '%Y-%m-%d %H:%i:%S') AS end_time,
                                    rc.member_id
                                FROM
                                    k_s_residual_card rc
                                    LEFT JOIN k_s_pro_order po ON rc.order_id = po.id
                                    LEFT JOIN k_s_card_type ct ON rc.card_id = ct.id
                                WHERE
                                    rc.state <> 0
                                    AND po.sys_Company_Code LIKE 'A76%'
                            ) M
                        WHERE
                            (
                                M.end_time > now()
                                OR M.end_time IS NULL
                            )
                            AND M.residue_num > 0
                            AND M.card_type = 9
                    ) vvv
                WHERE
                    vvv.member_id = m.id
            ) prices
        FROM
            k_s_member m
            LEFT JOIN (
                SELECT
                    sum(k.qk) arrears,
                    k.member_id
                FROM
                    (
                        SELECT
                            IFNULL(p.arrears, 0) - IFNULL(p.repayment, 0) - IFNULL(
                                (SUM(h.old_arrears) - SUM(h.new_arrears)),
                                0
                            ) qk,
                            p.member_id
                        FROM
                            k_s_payment_info p
                            LEFT JOIN k_s_change_repay_history h ON p.reqsn = h.orcode
                        WHERE
                            p.depart_org_code LIKE 'A76%'
                        GROUP BY
                            p.member_id,
                            p.reqsn
                    ) k
                GROUP BY
                    k.member_id
            ) b ON m.id = b.member_id
            LEFT JOIN (
                SELECT
                    SUM(
                        IFNULL(p.total_pay, 0) - IFNULL(odd_change, 0) - IFNULL(arrears, 0) - IFNULL(pay_give, 0) - IFNULL(cash_moment, 0) + IFNULL(p.repayment, 0)
                    ) noCZ_totalpay,
                    p.member_id
                FROM
                    k_s_payment_info p
                WHERE
                    p.depart_org_code LIKE 'A76%'
                    AND p.reqsn NOT LIKE '%CZ%'
                GROUP BY
                    p.member_id
            ) aa ON m.id = aa.member_id
            LEFT JOIN (
                SELECT
                    sum(IFNULL(s.price, 0)) price,
                    r.member_id
                FROM
                    k_s_refund r
                    JOIN k_s_refund_order s ON r.id = s.r_id
                WHERE
                    (
                        r.refund_type = 0
                        OR r.refund_type = 1
                    )
                    AND r.apply_state = 1
                GROUP BY
                    r.member_id
            ) z ON m.id = z.member_id
            JOIN (
                SELECT
                    org_id
                FROM
                    t_s_user_org
                WHERE
                    user_id = 'ff8080817303649c0173082d61cb012c'
            ) mm ON m.depart_name = mm.org_id
        WHERE
            m.sys_Company_Code LIKE 'A76%'
    ) zzz
LIMIT
    0, 10