<?php
	$tel = "010-2777-3333";
	echo "\$tel : $tel<br>";
	$num_tel = strlen($tel);   // strlen() : 문자열 길이 계산

	echo "\$tel의 길이 : $num_tel<br>";

	$tel1 = substr($tel, 0, 3);   // 앞에서 3개의 문자 추출
	echo "$tel1<br>";
	$tel2 = substr($tel, 4, 4);   // 앞에서 4번째에서 4개의 문자 추출
	echo "$tel2<br>";
	$tel3 = substr($tel, 9, 4);   // 앞에서 9번째에서 4개의 문자 추출
	echo "$tel3<br>";

	$phone = explode("-", $tel);

	echo "전화번호 : $phone[0] $phone[1] $phone[2] <br>";
?>